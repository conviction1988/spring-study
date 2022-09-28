package com.spring.study.domain.order.domain;

import com.spring.study.domain.product.domain.Product;
import com.spring.study.domain.user.domain.User;
import com.spring.study.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false)
    Product product;

    @Builder
    public Order(User user, Product product) {
        this.user = user;
        this.product = product;
    }

}
