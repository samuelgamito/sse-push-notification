package com.sse.publisher.helpers;

import com.sse.publisher.controller.vo.response.EventResponse;
import com.sse.publisher.controller.vo.response.EventStreamResponse;
import com.sse.publisher.models.EventDatabaseModel;
import com.sse.publisher.models.EventNotificationModel;

import java.util.List;
import java.util.stream.Collectors;

public class EventStreamResponseBuilder {

    private final EventStreamResponse eventStreamResponse;

    private EventStreamResponseBuilder(final String type){
        super();
        this.eventStreamResponse = new EventStreamResponse(type);
    }

    public static EventStreamResponseBuilder getBuilderSimpleEvent(){

        return new EventStreamResponseBuilder("EVENT");
    }

    public static EventStreamResponseBuilder getBuilderInitialEvent(){

        return new EventStreamResponseBuilder("INITIAL");
    }

    public static EventStreamResponseBuilder getBuilderErrorEvent(){

        return new EventStreamResponseBuilder("ERROR");
    }

    public EventStreamResponseBuilder setMessage(final String message){
        this.eventStreamResponse.setMessage(message);
        return this;
    }

    public EventStreamResponseBuilder setListOfEvents(final List<EventNotificationModel> eventResponses){

        this.eventStreamResponse.setEventResponses(eventResponses.stream().map(EventResponse::new).collect(Collectors.toList()));
        return this;
    }

    public EventStreamResponse build(){
        return this.eventStreamResponse;
    }

    public EventStreamResponseBuilder setListOfEventsDatabase(final List<EventDatabaseModel> eventDatabaseModels) {

        this.eventStreamResponse.setEventResponses(eventDatabaseModels.stream().map(EventResponse::new).collect(Collectors.toList()));

        return this;
    }
}
