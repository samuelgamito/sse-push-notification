package com.sse.publisher.controller.handlers;

import com.sse.publisher.controller.vo.response.ErrorResponse;
import com.sse.publisher.exceptions.ExceptionType;
import com.sse.publisher.exceptions.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionInterceptor.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException ex, final WebRequest request) {
        final String[] errors = Objects.requireNonNull(ex.getMessage()).split(":");

        final String errorMsg = errors[0];


        return new ErrorResponse(ExceptionType.BAD_REQUEST, List.of(errorMsg));
    }

    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ErrorResponse> asdas2(
            final GlobalException ex, final WebRequest request) {
        final ExceptionType exceptionType = ex.getType();
        ex.printLog();
        final ErrorResponse errBody =  new ErrorResponse(exceptionType);
        return ResponseEntity.status(exceptionType.getStatusCode()).body(errBody);
    }


}
