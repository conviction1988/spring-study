package com.spring.study.global.exception;

import com.spring.study.global.common.response.CommonResult;
import com.spring.study.global.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    private final MessageSource messageSource;


    @ExceptionHandler(CInternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult internalServerException(CInternalServerException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage(e.getMessageKey() + ".code")),
                getMessage(e.getMessageKey() + ".message", e.getArgs()));
    }

    @ExceptionHandler(CConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResult conflictException(CConflictException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage(e.getMessageKey() + ".code")),
                getMessage(e.getMessageKey() + ".message", e.getArgs()));
    }

    @ExceptionHandler(CNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult notFoundException(CNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage(e.getMessageKey() + ".code")),
                getMessage(e.getMessageKey() + ".message", e.getArgs()));
    }

    @ExceptionHandler(CUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult unauthorizedException(CUnauthorizedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage(e.getMessageKey() + ".code")),
                getMessage(e.getMessageKey() + ".message", e.getArgs()));
    }

    @ExceptionHandler(CBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult badRequestException(CBadRequestException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage(e.getMessageKey() + ".code")),
                getMessage(e.getMessageKey() + ".message", e.getArgs()));
    }


    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
