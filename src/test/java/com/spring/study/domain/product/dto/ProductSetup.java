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

        final Product product = productRepository.save(Product.builder()
                .name("Spring Boot")
                .currency(EntityEnum.Currency.KRW)
                .price(BigDecimal.valueOf(10000))
                .build());
        return product;
    }
}
