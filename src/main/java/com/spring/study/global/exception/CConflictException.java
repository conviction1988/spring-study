package com.spring.study.global.exception;

import lombok.Getter;

public class CConflictException extends RuntimeException {

    @Getter
    private String messageKey;

    @Getter
    private Object[] args = new Object[]{""};

    public CConflictException(String message, Throwable t) {
        super(message, t);
    }

    public CConflictException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public CConflictException(String messageKey, Object[] args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public CConflictException(String messageKey) {
        super("");
        this.messageKey = messageKey;
    }

    public CConflictException() {
        super();
    }
}
