package com.sse.publisher.controller.vo.request;

public class EventHistoryQueryParam {

    private String alias;
    private String traceId;
    private String action;

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(final String traceId) {
        this.traceId = traceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }
}
