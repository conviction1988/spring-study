package com.spring.study.domain.order.dto;

import com.spring.study.domain.order.domain.Order;
import com.spring.study.domain.order.repository.OrderRepository;
import com.spring.study.domain.product.domain.Product;
import com.spring.study.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderSetup {

    private final OrderRepository orderRepository;

    public Order save(User user, Product product) {

        final Order order = orderRepository.save(Order.builder()
                .user(user)
                .product(product)
                .build());
        return order;
    }
}
