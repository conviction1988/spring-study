package com.spring.study.domain.product.dto;

import com.spring.study.domain.product.domain.Product;
import com.spring.study.domain.product.repository.ProductRepository;
import com.spring.study.global.common.entity.EntityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class ProductSetup {

    private final ProductRepository productRepository;

    public Product save() {

        String name = "Spring Boot";
        BigDecimal price = BigDecimal.valueOf(10000);
        EntityEnum.Currency currency = EntityEnum.Currency.KRW;

        final Product product = productRepository.save(Product.builder()
                .name(name)
                .currency(currency)
                .price(price)
                .build());
        return product;
    }
}
