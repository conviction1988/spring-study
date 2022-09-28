package com.spring.study.domain.product.dto;

import com.spring.study.domain.product.domain.Product;
import com.spring.study.global.common.entity.EntityEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private String name;

    private BigDecimal price;

    private EntityEnum.Currency currency;

    public ProductResponse(final Product product) {

        if (!ObjectUtils.isEmpty(product)) {
            this.name = product.getName();
            this.currency = product.getCurrency();
            this.price = product.getPrice();
        }
    }

}
