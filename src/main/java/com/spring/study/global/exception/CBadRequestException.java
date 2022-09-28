package com.spring.study.global.exception;

import lombok.Getter;

public class CBadRequestException extends RuntimeException {

    @Getter
    private String messageKey;

    @Getter
    private Object[] args = new Object[]{""};

    public CBadRequestException(String message, Throwable t) {
        super(message, t);
    }

    public CBadRequestException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public CBadRequestException(String messageKey, Object[] args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public CBadRequestException(String messageKey) {
        super("");
        this.messageKey = messageKey;
    }

    public CBadRequestException() {
        super();
    }
}
