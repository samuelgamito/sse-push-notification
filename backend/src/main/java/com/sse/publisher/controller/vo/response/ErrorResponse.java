package com.sse.publisher.controller.vo.response;

import com.sse.publisher.exceptions.ExceptionType;

import java.util.List;

public class ErrorResponse {
    private String code;
    private String message;
    private List<String> details;

    public ErrorResponse(final ExceptionType type, final List<String> details, final Object... args){
        this.code = type.getCode();
        this.message = type.getFormattedMessage(args);
        this.details = details;
    }

    public ErrorResponse(final ExceptionType type, final Object... args){
        this.code = type.getCode();
        this.message = type.getFormattedMessage(args);
        this.details = null;
    }



    public List<String> getDetails() {
        return details;
    }

    public void setDetails(final List<String> details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
