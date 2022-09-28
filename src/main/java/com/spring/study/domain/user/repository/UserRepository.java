package com.spring.study.domain.user.repository;

import com.spring.study.domain.user.domain.User;
import com.spring.study.global.common.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(Email email);
    boolean existsByEmail(Email email);
}
