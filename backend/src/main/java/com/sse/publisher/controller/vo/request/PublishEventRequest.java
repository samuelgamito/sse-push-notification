package com.sse.publisher.controller.vo.request;

import javax.validation.constraints.NotBlank;

public class PublishEventRequest {
    @NotBlank
    private String alias;

    @NotBlank
    private String message;

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



}
