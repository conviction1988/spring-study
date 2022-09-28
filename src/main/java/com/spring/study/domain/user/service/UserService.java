package com.spring.study.domain.user.service;

import com.spring.study.domain.user.domain.User;
import com.spring.study.domain.user.repository.UserRepository;
import com.spring.study.global.config.security.SecurityUserDetail;
import com.spring.study.global.exception.CNotFoundException;
import com.spring.study.global.exception.CUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(final Long id) {
        final Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new CNotFoundException("userNotFound"));
        return user.get();
    }

    @Transactional(readOnly = true)
    public User me(SecurityUserDetail securityUserDetail) {

        if (ObjectUtils.isEmpty(securityUserDetail)) {
            throw new CUnauthorizedException("entryPointException");
        }
        return this.findById(securityUserDetail.getId());
    }
}
