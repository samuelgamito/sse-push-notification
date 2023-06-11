package com.sse.publisher.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.publisher.constants.HttpConstants;
import com.sse.publisher.controller.vo.request.PublishEventRequest;
import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import com.sse.publisher.models.EventModel;
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


    private void setPublishAction(final EventModel eventModel, final String traceId){
        final Map<String, String> metadata = new HashMap<>();
        metadata.put("action", "published");
        metadata.put("traceId", traceId);
        eventModel.setMetadata(metadata);
    }
    private void setIgnoredAction(final EventModel eventModel, final String traceId){
        final Map<String, String> metadata = new HashMap<>();
        metadata.put("action", "ignored");
        metadata.put("traceId", traceId);
        eventModel.setMetadata(metadata);
    }

    public void publish(final EventModel eventModel){

        final OffsetDateTime timeNow = OffsetDateTime.now();
        final EventSettingsModel eventInfo = eventSettingsRepository.getByAlias(eventModel.getAlias());

        final String traceId = httpServletRequest.getHeader(HttpConstants.TRACE_ID_HEADER);

        eventModel.setPublishedAt(timeNow);

        try {
            if(eventInfo != null){
                messagePublisher.send(
                        eventInfo.getRoutingKey(),
                        objectMapper.writeValueAsString(eventModel).getBytes(),
                        traceId);

                setPublishAction(eventModel, traceId);
                eventRepository.saveEvent(eventModel);
            }else{
                setIgnoredAction(eventModel, traceId);
                eventRepository.saveEvent(eventModel);
            }

        } catch (JsonProcessingException e) {

            throw GlobalException.getBuilder(LOGGER).setExceptionType(ExceptionType.JSON_PARSER_ERROR).setDetailedError(e);
        }
    }

    public List<EventModel> getHistory() {

        return eventRepository.getHistory();
    }
}
