package com.spring.study.domain.auth.service;

import com.spring.study.domain.auth.dto.GenerateTokenRequest;
import com.spring.study.domain.auth.dto.SignInRequest;
import com.spring.study.domain.auth.dto.TokenResponse;
import com.spring.study.domain.user.domain.User;
import com.spring.study.domain.user.dto.SignUpRequest;
import com.spring.study.domain.user.repository.UserRepository;
import com.spring.study.global.common.entity.EntityEnum;
import com.spring.study.global.common.model.Email;
import com.spring.study.global.config.jwt.JwtTokenProvider;
import com.spring.study.global.config.security.SecurityUserDetail;
import com.spring.study.global.exception.CConflictException;
import com.spring.study.global.exception.CInternalServerException;
import com.spring.study.global.exception.CNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.token.refresh-token-expire-length}")
    private long refreshTokenExpireTime;

    @Transactional(rollbackFor = {Exception.class})
    public User signUp(SignUpRequest request){

          if(userRepository.existsByEmail(Email.of(request.getEmail()))) {
              throw new CConflictException("existingUser");
          }

          User user = userRepository.save(request.toEntity(EntityEnum.UserRole.ROLE_USER, bCryptPasswordEncoder));
          if (ObjectUtils.isEmpty(user)) {
              throw new CInternalServerException("unKnown");
          }
          return user;
    }

    @Transactional(rollbackFor = {Exception.class})
    public TokenResponse signIn(SignInRequest request) {

        Optional<User> user = userRepository.findByEmail(Email.of(request.getEmail()));
        if (user.isEmpty()) {
            throw new CNotFoundException("userNotFound");
        }

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new CInternalServerException("userPasswordNotMatch");
        }

        try {
            String refreshToken = jwtTokenProvider.generateRefreshToken(request.getEmail());
            TokenResponse tokenResponse = new TokenResponse(jwtTokenProvider.generateAccessToken(request.getEmail()),
                    refreshToken);

            redisTemplate.opsForValue().set(request.getEmail(), refreshToken, refreshTokenExpireTime,
                    TimeUnit.MILLISECONDS);

            return tokenResponse;
        } catch (Exception e) {
            throw new CInternalServerException("invalidRefreshTokenError");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public TokenResponse regenerateToken(GenerateTokenRequest request) {

        String refreshToken = request.getRefreshToken();
        try {
            if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
                throw new CInternalServerException("invalidRefreshTokenError");
            }

        Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(refreshToken);

        String refreshTokenByRedis = redisTemplate.opsForValue().get(authentication.getName());
        if(!refreshTokenByRedis.equals(refreshToken)) {
            throw new CInternalServerException("notMatchRefreshToken");
        }

        String generateRefreshToken = jwtTokenProvider.generateRefreshToken(authentication.getName());
        TokenResponse tokenResponse = new TokenResponse(jwtTokenProvider.generateAccessToken(authentication.getName()),
                generateRefreshToken);

        redisTemplate.opsForValue().set(authentication.getName(), generateRefreshToken, refreshTokenExpireTime,
                TimeUnit.MILLISECONDS);
        return tokenResponse;

      } catch (AuthenticationException e) {
            throw new CInternalServerException("invalidRefreshTokenError");
      }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteRefreshToken(SecurityUserDetail securityUserDetail) {

        SecurityContextHolder.clearContext();
        redisTemplate.delete(securityUserDetail.getUsername());
    }

}
