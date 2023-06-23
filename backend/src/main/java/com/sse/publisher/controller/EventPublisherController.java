package com.sse.publisher.controller;

import com.sse.publisher.controller.vo.request.EventHistoryQueryParam;
import com.sse.publisher.controller.vo.request.PublishEventRequest;
import com.sse.publisher.controller.vo.response.EventResponse;
import com.sse.publisher.models.EventDatabaseModel;
import com.sse.publisher.models.EventNotificationModel;
import com.sse.publisher.services.EventPublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
public class EventPublisherController {


    private final EventPublisherService eventPublisherService;

    public EventPublisherController(final EventPublisherService eventPublisherService) {
        this.eventPublisherService = eventPublisherService;
    }

    @PostMapping(path= "/")
    public ResponseEntity<Void> publishEvent(@RequestBody @Validated final PublishEventRequest publishEventRequest) {

        final EventNotificationModel eventNotificationModel = new EventNotificationModel();
        eventNotificationModel.setAlias(publishEventRequest.getAlias());
        eventNotificationModel.setMessage(publishEventRequest.getMessage());

        eventPublisherService.publish(eventNotificationModel);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path= "/history")
    public ResponseEntity<List<EventResponse>> getEventHistory(final EventHistoryQueryParam queryParam) {

        final List<EventDatabaseModel> eventDatabaseModels = eventPublisherService.getHistory();

        return ResponseEntity.status(HttpStatus.OK)
                .body(eventDatabaseModels.stream().map(EventResponse::new).collect(Collectors.toList()));
    }
}
