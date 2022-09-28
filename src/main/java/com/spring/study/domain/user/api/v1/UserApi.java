package com.spring.study.domain.user.api.v1;

import com.spring.study.domain.user.dto.UserResponse;
import com.spring.study.domain.user.service.UserService;
import com.spring.study.global.common.response.SingleResult;
import com.spring.study.global.common.service.ResponseService;
import com.spring.study.global.config.security.SecurityUserDetail;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("v1UserApi")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApi {

    private final ResponseService responseService;
    private final UserService userService;

    @GetMapping("/{id}")
    public SingleResult<UserResponse> getUser(@PathVariable long id) {
        return responseService.getSingleResult(new UserResponse(userService.findById(id)));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public SingleResult<UserResponse> me(
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetail securityUserDetail) {
        return responseService.getSingleResult(new UserResponse(userService.me(securityUserDetail)));
    }

}
