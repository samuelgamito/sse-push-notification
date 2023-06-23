package com.sse.publisher.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.publisher.constants.HttpConstants;
import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import com.sse.publisher.models.EventDatabaseModel;
import com.sse.publisher.models.EventNotificationModel;
import com.sse.publisher.models.EventSettingsModel;
import com.sse.publisher.publishers.MessagePublisher;
import com.sse.publisher.repositories.EventRepository;
import com.sse.publisher.repositories.EventSettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventPublisherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventPublisherService.class);
    private final EventSettingsRepository eventSettingsRepository;
    private final EventRepository eventRepository;
    private final MessagePublisher messagePublisher;
    private final  HttpServletRequest httpServletRequest;
    private final ObjectMapper objectMapper;

    public EventPublisherService(final EventSettingsRepository eventSettingsRepository, final EventRepository eventRepository, final MessagePublisher messagePublisher, final HttpServletRequest httpServletRequest, final ObjectMapper objectMapper) {
        this.eventSettingsRepository = eventSettingsRepository;
        this.eventRepository = eventRepository;
        this.messagePublisher = messagePublisher;
        this.httpServletRequest = httpServletRequest;
        this.objectMapper = objectMapper;
    }


    private Map<String,String> getPublishAction(final String traceId, final String routingKey){
        final Map<String, String> metadata = new HashMap<>();
        metadata.put("action", "published");
        metadata.put("traceId", traceId);
        metadata.put("routingKey", routingKey);
       return metadata;
    }
    private Map<String,String> getIgnoredAction(final String traceId){
        final Map<String, String> metadata = new HashMap<>();
        metadata.put("action", "ignored");
        metadata.put("traceId", traceId);
        return metadata;
    }

    public void publish(final EventNotificationModel eventNotificationModel){

        final OffsetDateTime timeNow = OffsetDateTime.now();
        final EventSettingsModel eventInfo = eventSettingsRepository.getByAlias(eventNotificationModel.getAlias());

        final String traceId = httpServletRequest.getHeader(HttpConstants.TRACE_ID_HEADER);

        eventNotificationModel.setPublishedAt(timeNow);

        try {
            if(eventInfo != null){
                messagePublisher.send(
                        eventInfo.getRoutingKey(),
                        objectMapper.writeValueAsString(eventNotificationModel).getBytes(),
                        traceId);


                sendEventToDatabase(eventNotificationModel,getPublishAction(traceId, eventInfo.getRoutingKey()));
            }else{
                sendEventToDatabase(eventNotificationModel,getIgnoredAction(traceId));
            }

        } catch (JsonProcessingException e) {

            throw GlobalException.getBuilder(LOGGER).setExceptionType(ExceptionType.JSON_PARSER_ERROR).setDetailedError(e);
        }
    }

    private void sendEventToDatabase(final EventNotificationModel eventNotificationModel,
                                     final Map<String, String> metadata){

        final EventDatabaseModel eventDatabaseModel = new EventDatabaseModel(eventNotificationModel);
        eventDatabaseModel.setMetadata(metadata);

        eventRepository.saveEvent(eventDatabaseModel);

    }

    public List<EventDatabaseModel> getHistory() {

        return eventRepository.getHistory();
    }
}
