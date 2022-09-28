package com.spring.study.domain.auth.api.v1;

import com.spring.study.domain.auth.dto.GenerateTokenRequest;
import com.spring.study.domain.auth.dto.SignInRequest;
import com.spring.study.domain.auth.dto.TokenResponse;
import com.spring.study.domain.auth.service.AuthService;
import com.spring.study.domain.user.dto.SignUpRequest;
import com.spring.study.domain.user.dto.UserResponse;
import com.spring.study.global.common.response.CommonResult;
import com.spring.study.global.common.response.SingleResult;
import com.spring.study.global.common.service.ResponseService;
import com.spring.study.global.config.security.SecurityUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("v1AuthApi")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final ResponseService responseService;
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "")
    @PostMapping("/sign-up")
    public CommonResult signUp(@RequestBody @Valid final SignUpRequest request) {
        return responseService.getSingleResult(new UserResponse(authService.signUp(request)));
    }

    @Operation(summary = "로그인", description = "")
    @PostMapping("/sign-in")
    public SingleResult<TokenResponse> signIn(@RequestBody @Valid final SignInRequest request) {
        return responseService.getSingleResult(authService.signIn(request));
    }

    @PostMapping("/regenerate-token")
    public SingleResult<TokenResponse> regenerateToken(@RequestBody GenerateTokenRequest refreshTokenDto) {
        return responseService.getSingleResult(authService.regenerateToken(refreshTokenDto));
    }

    @Operation(summary = "로그아웃", description = "")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/sign-out")
    public CommonResult signOut(
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetail securityUserDetail) {
        authService.deleteRefreshToken(securityUserDetail);
        return responseService.getSuccessResult();
    }

}
