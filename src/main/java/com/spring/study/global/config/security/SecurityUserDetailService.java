package com.spring.study.global.config.security;

import com.spring.study.domain.user.domain.User;
import com.spring.study.domain.user.repository.UserRepository;
import com.spring.study.global.common.model.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(Email.of(userId));
        if (user.isPresent() == false) {
            throw new UsernameNotFoundException(userId + "is not found.");
        }
        return new SecurityUserDetail(user.get());
    }

}