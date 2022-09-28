package com.spring.study.domain.auth.dto;

public class SignInRequestBuilder {

    public static SignInRequest build(String email, String password) {
        return new SignInRequest(email, password);
    }

}