package com.sse.publisher.controller.vo.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sse.publisher.configs.OffsetDateTimeSerializer;
import com.sse.publisher.models.EventDatabaseModel;
import com.sse.publisher.models.EventNotificationModel;

import java.time.OffsetDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResponse {

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

    public EventResponse(){
        super();
    }

    public EventResponse(final EventDatabaseModel eventDatabaseModel){
        id = eventDatabaseModel.getId();
        alias = eventDatabaseModel.getAlias();
        message = eventDatabaseModel.getMessage();
        publishedAt = eventDatabaseModel.getPublishedAt();
        metadata = eventDatabaseModel.getMetadata();
        createdAt = eventDatabaseModel.getCreatedAt();
        updatedAt = eventDatabaseModel.getCreatedAt();
    }

    public EventResponse(final EventNotificationModel eventNotificationModel) {
        this.alias = eventNotificationModel.getAlias();
        this.message = eventNotificationModel.getMessage();
        this.publishedAt = eventNotificationModel.getPublishedAt();
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
