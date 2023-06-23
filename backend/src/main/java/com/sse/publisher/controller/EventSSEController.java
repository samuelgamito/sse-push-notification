package com.sse.publisher.controller;

import com.sse.publisher.controller.vo.response.EventStreamResponse;
import com.sse.publisher.models.EventNotificationModel;
import com.sse.publisher.services.EventSSEService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream")
public class EventSSEController {

    private final EventSSEService eventSSEService;

    public EventSSEController(final EventSSEService eventSSEService) {
        this.eventSSEService = eventSSEService;
    }

    @GetMapping(path = "/{username}",  produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventStreamResponse> streamNotification(@PathVariable final String username) {

        Flux<EventStreamResponse> f =  eventSSEService.consume(username);

        return Flux.<EventStreamResponse>never().mergeWith(f);
    }
}
