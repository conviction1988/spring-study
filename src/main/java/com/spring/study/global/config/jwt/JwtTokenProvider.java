package com.spring.study.global.config.jwt;

import com.spring.study.global.exception.CInternalServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.access-token-secret-key}")
    private String accessTokenSecretKey;

    @Value("${jwt.token.refresh-token-secret-key}")
    private String refreshTokenSecretKey;

    @Value("${jwt.token.access-token-expire-length}")
    private long accessTokenExpireTime;

    @Value("${jwt.token.refresh-token-expire-length}")
    private long refreshTokenExpireTime;

    private final UserDetailsService userDetailsService;

    public String generateAccessToken(String userId) {

        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + accessTokenExpireTime);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiresIn)
            .signWith(SignatureAlgorithm.HS256, accessTokenSecretKey)
            .compact();
    }

    public String generateRefreshToken(String userId) {

        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + refreshTokenExpireTime);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiresIn)
            .signWith(SignatureAlgorithm.HS256, refreshTokenSecretKey)
            .compact();
      }

    public Authentication getAuthenticationByAccessToken(String accessToken) {

        String userPrincipal = Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(accessToken)
                .getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Authentication getAuthenticationByRefreshToken(String refreshToken) {

        String userPrincipal = Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(refreshToken)
                .getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateAccessToken(String token) {

        try {
            Jwts.parser().setSigningKey(accessTokenSecretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            throw new CInternalServerException("invalidAccessToken");
        }
    }

    public boolean validateRefreshToken(String token) {

        try {
            Jwts.parser().setSigningKey(refreshTokenSecretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            throw new CInternalServerException("invalidRefreshTokenError");
        }
    }
}
