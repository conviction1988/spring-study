package com.spring.study.domain.order.dto;

import com.spring.study.domain.order.domain.Order;
import com.spring.study.domain.product.dto.ProductResponse;
import com.spring.study.domain.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {

    private Long id;
    private UserResponse user;
    private ProductResponse product;

    public OrderResponse(final Order order) {

        if (!ObjectUtils.isEmpty(order) && !ObjectUtils.isEmpty(order.getUser())
                && !ObjectUtils.isEmpty(order.getProduct())) {
            this.id = order.getId();
            this.user = new UserResponse(order.getUser());
            this.product = new ProductResponse(order.getProduct());
        }
    }

}
