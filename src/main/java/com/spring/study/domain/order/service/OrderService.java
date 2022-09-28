package com.spring.study.domain.order.service;

import com.spring.study.domain.order.domain.Order;
import com.spring.study.domain.order.dto.OrderResponse;
import com.spring.study.domain.order.repository.OrderRepository;
import com.spring.study.domain.product.domain.Product;
import com.spring.study.domain.product.service.ProductService;
import com.spring.study.domain.user.domain.User;
import com.spring.study.domain.user.service.UserService;
import com.spring.study.global.config.security.SecurityUserDetail;
import com.spring.study.global.exception.CInternalServerException;
import com.spring.study.global.exception.CNotFoundException;
import com.spring.study.global.exception.CUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public Page<OrderResponse> getUserOrders(SecurityUserDetail securityUserDetail, int page, int pageSize) {

        if (ObjectUtils.isEmpty(securityUserDetail)) {
            throw new CUnauthorizedException("entryPointException");
        }

        User user = userService.findById(securityUserDetail.getId());
        if (ObjectUtils.isEmpty(user)) {
            throw new CNotFoundException("resourceNotExist", new Object[]{"(User)"});
        }

        Page<Order> orders = orderRepository.findByUser(user, PageRequest.of(page - 1, pageSize));

        List<OrderResponse> orderResponse =
                orders.getContent().stream().map(OrderResponse::new).collect(Collectors.toList());

        return new PageImpl<>(orderResponse, orders.getPageable(), orders.getTotalElements());
    }

    @Transactional(rollbackFor = {Exception.class})
    public Order productOrder(SecurityUserDetail securityUserDetail, long productId) {

        if (ObjectUtils.isEmpty(securityUserDetail)) {
            throw new CUnauthorizedException("entryPointException");
        }

        User user = userService.findById(securityUserDetail.getId());
        Product product = productService.findById(productId);
        if (ObjectUtils.isEmpty(product)) {
            throw new CNotFoundException("resourceNotExist", new Object[]{"(Product)"});
        }

        Order order = orderRepository.save(Order.builder()
                .user(user)
                .product(product)
                .build());

        if (ObjectUtils.isEmpty(order)) {
            throw new CInternalServerException("unKnown");
        }

        return order;
    }
}
