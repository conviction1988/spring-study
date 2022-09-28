package com.spring.study.domain.user.api.v1;

import com.spring.study.domain.order.dto.OrderResponse;
import com.spring.study.domain.order.service.OrderService;
import com.spring.study.global.common.response.PageResult;
import com.spring.study.global.common.service.ResponseService;
import com.spring.study.global.config.security.SecurityUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("v1MyPageApi")
@RequestMapping("/api/v1/my-page")
@RequiredArgsConstructor
public class MyPageApi {

    private final ResponseService responseService;
    private final OrderService orderService;

    @Operation(summary = "회원 주문 내역 조회", description = "")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/orders")
    public PageResult<OrderResponse> getUserOrders(
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetail securityUserDetail,
            @Parameter(description = "페이지 [기본값 1]", required = true)
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @Parameter(description = "페이지 사이즈 [기본값 10]", required = true)
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return responseService.getPageResult(orderService.getUserOrders(securityUserDetail, page, pageSize));
    }

}
