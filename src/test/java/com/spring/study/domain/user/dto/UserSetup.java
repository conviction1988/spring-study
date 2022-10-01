package com.spring.study.domain.user.dto;

import com.spring.study.domain.user.domain.User;
import com.spring.study.domain.user.repository.UserRepository;
import com.spring.study.global.common.entity.EntityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSetup {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public User save() {

        final String email = "leescott@gmail.com";
        final String password = "P@ssw0rd";
        final String firstName = "lee";
        final String lastName = "scott";

        SignUpRequest request = SignUpRequestBuilder.build(email, password, firstName, lastName);

        final User user = userRepository.save(request.toEntity(EntityEnum.UserRole.ROLE_USER, bCryptPasswordEncoder));
        return user;
    }
}
