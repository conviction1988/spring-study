package com.spring.study.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GenerateTokenRequest {

    private String refreshToken;

    public GenerateTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
