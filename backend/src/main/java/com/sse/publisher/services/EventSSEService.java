package com.sse.publisher.services;

import com.sse.publisher.configs.factors.MessageListenerContainerFactory;
import com.sse.publisher.helpers.DeclarableBuilder;
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

import java.util.List;
import java.util.UUID;

@Service
public class EventSSEService {


    private final EventRepository eventRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final EventSettingsRepository eventSettingsRepository;
    private final AmqpAdmin amqpTemplate;
    private final QueueProperties queueProperties;

    private final MessageListenerContainerFactory messageListenerContainerFactory;

    public EventSSEService(final EventRepository eventRepository,
                           final UserSettingsRepository userSettingsRepository,
                           final EventSettingsRepository eventSettingsRepository,
                           final AmqpAdmin amqpTemplate,
                           final QueueProperties queueProperties,
                           final MessageListenerContainerFactory messageListenerContainerFactory) {

        this.eventRepository = eventRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.eventSettingsRepository = eventSettingsRepository;
        this.amqpTemplate = amqpTemplate;
        this.queueProperties = queueProperties;
        this.messageListenerContainerFactory = messageListenerContainerFactory;
    }

    public Flux<String> consume(final String username){

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

                                final String payload = new String(m.getBody());
                                emitter.next(payload);

                            });
                    emitter.onRequest(
                            v -> {
                                emitter.next("{\"type\":\"INITIAL_LOAD\", \"data\":\"Connection established.\"}");
                                mlc.start();
                            });
                    emitter.onDispose(mlc::stop);
                });
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
