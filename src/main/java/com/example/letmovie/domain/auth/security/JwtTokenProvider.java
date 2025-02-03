package com.example.letmovie.domain.auth.security;

import com.example.letmovie.domain.auth.service.TokenBlacklistService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
// Token 생성 및 유효성 체크 등 Token을 사용 시, 필요한 기능들을 정리해 놓은 클래스
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final TokenBlacklistService tokenBlacklistService;
    private SecretKey key;

    @PostConstruct  // jwtProperties가 초기화된 후 key를 설정하기 위한 annotation
    private void initKey() {
        if (this.key == null) {
            this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        }
    }

    public String createAccessToken(String email) {
        return createToken(email, jwtProperties.getAccessTokenValidity());
    }

    public String createRefreshToken(String email) {
        return createToken(email, jwtProperties.getRefreshTokenValidity());
    }

    private String createToken(String email, long validity) {
        initKey();
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        initKey();
        try {
            // 블랙리스트 확인
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                return false; // 블랙리스트에 포함된 토큰은 무효화
            }

            // JWT 서명 및 구조 검증
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);  // 토큰 파싱 및 서명 확인
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        initKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
