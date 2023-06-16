package com.sse.publisher.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IllegalFormatException;

public enum ExceptionType {

    DATABASE_ERROR("ERR-1", "Error during retrieving or inserting data at the database.", 500),
    BAD_REQUEST("ERR-2", "Invalid request.", 400),
    QUEUE_ERROR("ERR-3", "Error during communicate with RabbitMq.", 500),
    JSON_PARSER_ERROR("ERR-4", "Error during JSON parser.", 500),
    USER_ALREADY_CREATED("ERR-5", "User already created.", 409),
    USER_NOT_FOUND("ERR-5", "User not found.", 404);
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionType.class);
    private final String code;
    private final String message;
    private final int statusCode;

    ExceptionType(final String code, final String message, final int statusCode) {

        this.code = code;
        this.message = message;
        this.statusCode = statusCode;

    }

    public int getStatusCode(){
        return statusCode;
    }

    public String getCode() {

        return code;
    }

    public String getFormattedMessage(final Object... args) {

        if (message == null) {
            return "";
        }

        try {
            return String.format(message, args);
        } catch (final IllegalFormatException e) {
            LOGGER.warn(e.getMessage(), e);
            return message.replace("%s", "");
        }
    }
}
