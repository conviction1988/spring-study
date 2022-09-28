package com.spring.study.domain.product.service;

import com.spring.study.domain.product.domain.Product;
import com.spring.study.domain.product.repository.ProductRepository;
import com.spring.study.global.exception.CNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findById(final Long id) {

        final Optional<Product> product = productRepository.findById(id);
        product.orElseThrow(() -> new CNotFoundException("resourceNotExist", new Object[]{"(Product)"}));
        return product.get();
    }

}
