package com.spring.study.domain.product.domain;

import com.spring.study.global.common.entity.BaseEntity;
import com.spring.study.global.common.entity.EntityEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name="CURRENCY")
    private EntityEnum.Currency currency;

    @Builder
    public Product(String name, EntityEnum.Currency currency, BigDecimal price) {
        this.name = name;
        this.currency = currency;
        this.price = price;
    }

}
