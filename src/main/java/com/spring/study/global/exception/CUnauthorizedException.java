package com.spring.study.global.exception;

import lombok.Getter;

public class CUnauthorizedException extends RuntimeException {

    @Getter
    private String messageKey;

    @Getter
    private Object[] args = new Object[]{""};

    public CUnauthorizedException(String message, Throwable t) {
        super(message, t);
    }

    public CUnauthorizedException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public CUnauthorizedException(String messageKey, Object[] args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public CUnauthorizedException(String messageKey) {
        super("");
        this.messageKey = messageKey;
    }

    public CUnauthorizedException() {
        super();
    }
}
