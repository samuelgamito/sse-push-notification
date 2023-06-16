package com.sse.publisher.models;

import com.sse.publisher.controller.vo.request.UserRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
@Document
public class UserModel {

    @Id
    private String username;
    private List<String> alias = new ArrayList<>();

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

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

    public UserModel(final UserRequest userRequest) {
        this.username = userRequest.getUsername();
    }

    public UserModel() {
        super();
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
