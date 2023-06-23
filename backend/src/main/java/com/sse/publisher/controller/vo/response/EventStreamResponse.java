package com.sse.publisher.controller.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventStreamResponse {

    private String type;
    private String message;
    private List<EventResponse> eventResponses = new ArrayList<>();

    public EventStreamResponse(final String type) {
        this.type = type;
    }

    public EventStreamResponse(){
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<EventResponse> getEventResponses() {
        return eventResponses;
    }

    public void setEventResponses(final List<EventResponse> eventResponses) {
        this.eventResponses = eventResponses;
    }
}
