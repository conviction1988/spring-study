package com.spring.study.domain.product.dto;

import com.spring.study.domain.product.domain.Product;
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

    public ProductResponse(final Product product) {

        if (!ObjectUtils.isEmpty(product)) {
            this.name = product.getName();
            this.price = product.getPrice();
        }
    }

}
