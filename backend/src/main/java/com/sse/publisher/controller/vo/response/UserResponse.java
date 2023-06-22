package com.sse.publisher.controller.vo.response;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sse.publisher.configs.OffsetDateTimeSerializer;
import com.sse.publisher.models.UserModel;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserResponse {

    private String username;
    private List<String> alias = new ArrayList<>();
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime createdAt;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime updatedAt;
    public UserResponse(final UserModel user){
        this.alias = user.getAlias();
        this.username = user.getUsername();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(final List<String> alias) {
        this.alias = alias;
    }
}
