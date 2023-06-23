package com.sse.publisher.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.publisher.configs.factors.MessageListenerContainerFactory;
import com.sse.publisher.constants.RabbitConstants;
import com.sse.publisher.controller.vo.response.EventStreamResponse;
import com.sse.publisher.helpers.DeclarableBuilder;
import com.sse.publisher.helpers.EventStreamResponseBuilder;
import com.sse.publisher.models.EventDatabaseModel;
import com.sse.publisher.models.EventNotificationModel;
import com.sse.publisher.models.EventSettingsModel;
import com.sse.publisher.models.UserModel;
import com.sse.publisher.properties.QueueProperties;
import com.sse.publisher.repositories.EventRepository;
import com.sse.publisher.repositories.EventSettingsRepository;
import com.sse.publisher.repositories.UserSettingsRepository;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EventSSEService {


    private final EventRepository eventRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final EventSettingsRepository eventSettingsRepository;
    private final AmqpAdmin amqpTemplate;
    private final QueueProperties queueProperties;

    private final ObjectMapper objectMapper;

    private final MessageListenerContainerFactory messageListenerContainerFactory;

    public EventSSEService(final EventRepository eventRepository,
                           final UserSettingsRepository userSettingsRepository,
                           final EventSettingsRepository eventSettingsRepository,
                           final AmqpAdmin amqpTemplate,
                           final QueueProperties queueProperties,
                           final ObjectMapper objectMapper,
                           final MessageListenerContainerFactory messageListenerContainerFactory) {

        this.eventRepository = eventRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.eventSettingsRepository = eventSettingsRepository;
        this.amqpTemplate = amqpTemplate;
        this.queueProperties = queueProperties;
        this.objectMapper = objectMapper;
        this.messageListenerContainerFactory = messageListenerContainerFactory;
    }

    public Flux<EventStreamResponse> consume(final String username){

        final UserModel userInfo = userSettingsRepository.getById(username);
        final List<EventSettingsModel> eventSettingsModelList =
                eventSettingsRepository.getByMultipleAlias(userInfo.getAlias());

        final String queueName = buildQueue(userInfo.getUsername(), eventSettingsModelList);
        final MessageListenerContainer mlc =
                messageListenerContainerFactory.createMessageListenerContainer(queueName);


        return Flux.create(
                emitter -> {
                    mlc.setupMessageListener(
                            m -> {


                                final String traceId =
                                        m.getMessageProperties().getHeader(RabbitConstants.TRACE_ID_HEADER);
                                final String routingKey = m.getMessageProperties().getReceivedRoutingKey();
                                try {
                                    final EventNotificationModel eventNotificationModel =
                                            objectMapper.readValue(m.getBody(), EventNotificationModel.class);

                                    sendEventToDatabase(eventNotificationModel, getConsumedAction(traceId, username, routingKey));

                                    final EventStreamResponseBuilder eventStreamResponseBuilder
                                            = EventStreamResponseBuilder
                                            .getBuilderSimpleEvent()
                                                    .setListOfEvents(Collections.singletonList(eventNotificationModel));


                                    emitter.next(eventStreamResponseBuilder.build());
                                } catch (IOException e) {
                                    final EventStreamResponseBuilder eventStreamResponseBuilder
                                            = EventStreamResponseBuilder
                                            .getBuilderErrorEvent()
                                            .setMessage("Error on parsing message");
                                    emitter.next(eventStreamResponseBuilder.build());
                                }



                            });
                    emitter.onRequest(
                            v -> {
                                final List<EventDatabaseModel> eventDatabaseModels
                                        = eventRepository.getUserHistoryLatest(username);
                                final EventStreamResponseBuilder eventStreamResponseBuilder
                                        = EventStreamResponseBuilder
                                        .getBuilderInitialEvent()
                                        .setListOfEventsDatabase(eventDatabaseModels);
                                emitter.next(eventStreamResponseBuilder.build());
                                mlc.start();
                            });
                    emitter.onDispose(mlc::stop);
                });
    }

    private Map<String,String> getConsumedAction(final String traceId, final String userId, final String routingKey){
        final Map<String, String> metadata = new HashMap<>();
        metadata.put("action", "consumed");
        metadata.put("traceId", traceId);
        metadata.put("userId", userId);
        metadata.put("routingKey", routingKey);
        return metadata;
    }
    private void sendEventToDatabase(final EventNotificationModel eventNotificationModel,
                                     final Map<String, String> metadata){

        final EventDatabaseModel eventDatabaseModel = new EventDatabaseModel(eventNotificationModel);
        eventDatabaseModel.setMetadata(metadata);

        eventRepository.saveEvent(eventDatabaseModel);

    }
    private String buildQueue(final String username, final  List<EventSettingsModel> userNotificationsSettings) {

        final UUID uuid = UUID.randomUUID();
        final String queueName = String.format("%s-%s", username, uuid);

        final DeclarableBuilder declarableBuilder =
                DeclarableBuilder.getBuilder(amqpTemplate)
                        .createQueue(queueName)
                        .defineExchange(queueProperties.getExchange());

        for (EventSettingsModel s : userNotificationsSettings) {
            declarableBuilder.addBinding(s.getRoutingKey());
        }

        return queueName;
    }

}
