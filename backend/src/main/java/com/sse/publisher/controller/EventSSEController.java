package com.sse.publisher.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
public class EventSSEController {

//    @GetMapping(path = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> streamNotification() {
//
//        Flux<String> f ;
//        return Flux.<String>never().mergeWith(f);
//    }
}
