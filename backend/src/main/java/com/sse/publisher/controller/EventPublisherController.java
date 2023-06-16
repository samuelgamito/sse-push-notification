package com.sse.publisher.controller;

import com.sse.publisher.controller.vo.request.EventHistoryQueryParam;
import com.sse.publisher.controller.vo.request.PublishEventRequest;
import com.sse.publisher.controller.vo.response.EventModelResponse;
import com.sse.publisher.models.EventModel;
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

        final EventModel eventModel = new EventModel();
        eventModel.setAlias(publishEventRequest.getAlias());
        eventModel.setMessage(publishEventRequest.getMessage());

        eventPublisherService.publish(eventModel);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path= "/history")
    public ResponseEntity<List<EventModelResponse>> getEventHistory(final EventHistoryQueryParam queryParam) {

        final List<EventModel> eventModels = eventPublisherService.getHistory();

        return ResponseEntity.status(HttpStatus.OK)
                .body(eventModels.stream().map(EventModelResponse::new).collect(Collectors.toList()));
    }
}
