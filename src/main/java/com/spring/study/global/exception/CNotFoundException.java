package com.spring.study.global.exception;

import lombok.Getter;

public class CNotFoundException extends RuntimeException {

    @Getter
    private String messageKey;

    @Getter
    private Object[] args = new Object[]{""};

    public CNotFoundException(String message, Throwable t) {
        super(message, t);
    }

    public CNotFoundException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public CNotFoundException(String messageKey, Object[] args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public CNotFoundException(String messageKey) {
        super("");
        this.messageKey = messageKey;
    }

    public CNotFoundException() {
        super();
    }
}
