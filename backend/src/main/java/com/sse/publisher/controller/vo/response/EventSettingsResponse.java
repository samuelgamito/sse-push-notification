package com.sse.publisher.controller.vo.response;


import com.sse.publisher.models.EventSettingsModel;

public class EventSettingsResponse {
    private String description;
    private String alias;

    private String routingKey;

    public EventSettingsResponse(){
        super();
    }

    public EventSettingsResponse(final EventSettingsModel model){
        this.description = model.getDescription();
        this.alias = model.getAlias();
        this.routingKey = model.getRoutingKey();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(final String routingKey) {
        this.routingKey = routingKey;
    }
}
