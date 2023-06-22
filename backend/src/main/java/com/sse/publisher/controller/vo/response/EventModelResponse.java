package com.sse.publisher.controller.vo.response;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sse.publisher.configs.OffsetDateTimeSerializer;
import com.sse.publisher.models.EventModel;

import java.time.OffsetDateTime;
import java.util.Map;

public class EventModelResponse {

    private String id;

    private String alias;

    private String message;

    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime publishedAt;

    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime createdAt;

    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime updatedAt;

    private Map<String, String> metadata;

    public EventModelResponse(){
        super();
    }

    public EventModelResponse(final EventModel eventModel){
        id = eventModel.getId();
        alias = eventModel.getAlias();
        message = eventModel.getMessage();
        publishedAt = eventModel.getPublishedAt();
        createdAt = eventModel.getCreatedAt();
        updatedAt = eventModel.getCreatedAt();
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(final Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(final OffsetDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
