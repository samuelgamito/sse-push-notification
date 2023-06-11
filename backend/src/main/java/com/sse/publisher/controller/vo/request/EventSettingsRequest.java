package com.sse.publisher.controller.vo.request;

import javax.validation.constraints.NotBlank;

public class EventSettingsRequest {
    @NotBlank
    private String alias;

    @NotBlank
    private String description;


    public String getAlias() {
        return alias;
    }

    public void setAlias(final String message) {
        this.alias = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
