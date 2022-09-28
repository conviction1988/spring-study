package com.spring.study.global.exception;

import lombok.Getter;

public class CInternalServerException extends RuntimeException {

    @Getter
    private String messageKey;

    @Getter
    private Object[] args = new Object[]{""};

    public CInternalServerException(String message, Throwable t) {
        super(message, t);
    }

    public CInternalServerException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public CInternalServerException(String messageKey, Object[] args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public CInternalServerException(String messageKey) {
        super("");
        this.messageKey = messageKey;
    }

    public CInternalServerException() {
        super();
    }
}
