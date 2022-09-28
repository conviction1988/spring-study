package com.spring.study.domain.product.api.v1;

import com.spring.study.domain.order.dto.OrderResponse;
import com.spring.study.domain.order.service.OrderService;
import com.spring.study.domain.product.dto.ProductResponse;
import com.spring.study.domain.product.service.ProductService;
import com.spring.study.global.common.response.SingleResult;
import com.spring.study.global.common.service.ResponseService;
import com.spring.study.global.config.security.SecurityUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("v1ProductApi")
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ResponseService responseService;
    private final ProductService productService;
    private final OrderService orderService;

    @Operation(summary = "상품 조회", description = "")
    @GetMapping("/{id}")
    public SingleResult<ProductResponse> getProduct(@PathVariable long id) {
        return responseService.getSingleResult(new ProductResponse(productService.findById(id)));
    }

    @Operation(summary = "상품 주문", description = "")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{id}/orders")
    public SingleResult<OrderResponse> productOrder(
            @Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetail securityUserDetail,
            @PathVariable long id) {
        return responseService.getSingleResult(new OrderResponse(orderService.productOrder(securityUserDetail, id)));
    }
}
