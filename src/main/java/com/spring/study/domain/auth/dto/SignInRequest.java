package com.spring.study.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SignInRequest {

    @NotEmpty(message = "Please enter your Email")
    @Email
    private String email;
    @NotEmpty(message = "Please enter your Password")
    private String password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
