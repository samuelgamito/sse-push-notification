package com.sse.publisher.controller.vo.response;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sse.publisher.configs.OffsetDateTimeSerializer;
import com.sse.publisher.models.EventSettingsModel;

import java.time.OffsetDateTime;

public class EventSettingsResponse {
    private String description;
    private String alias;

    private String routingKey;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime createdAt;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime updatedAt;

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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
