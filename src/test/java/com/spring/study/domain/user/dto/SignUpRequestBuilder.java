package com.spring.study.domain.user.dto;

public class SignUpRequestBuilder {

    public static SignUpRequest build(String email, String password, String firstName, String lastName) {
        return new SignUpRequest(email, password, firstName, lastName);
    }

}