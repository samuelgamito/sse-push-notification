package com.sse.publisher.exceptions;

import org.slf4j.Logger;

public class GlobalException extends RuntimeException{

    private final Logger LOGGER;
    private String formattedErrorMessage;
    private Throwable detailedError;
    private ExceptionType type;


    private GlobalException(final Logger logger) {
        LOGGER = logger;
    }

    public static GlobalException getBuilder(final Logger logger){
        return new GlobalException(logger);
    }

    public GlobalException setExceptionType(final ExceptionType type ){
        this.type = type;

        return this;
    }



    public ExceptionType getType(){
        return type;
    }

    public void printLog(){
        LOGGER.error(formattedErrorMessage);
        if(LOGGER.isDebugEnabled()) {
            detailedError.printStackTrace();
        }
    }

    public GlobalException setFormattedErrorMessage(final String formattedErrorMessage) {
        this.formattedErrorMessage = formattedErrorMessage;
        return this;
    }

    public GlobalException setDetailedError(final Throwable detailedError) {
        this.detailedError = detailedError;
        return this;
    }
}
