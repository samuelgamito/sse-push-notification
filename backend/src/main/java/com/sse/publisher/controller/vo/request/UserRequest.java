package com.sse.publisher.controller.vo.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserRequest {
    @NotBlank
    private String username;

    private List<String> alias;

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(final List<String> alias) {
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
