package com.sse.publisher.controller.vo.request;

import javax.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
