package com.example.letmovie.domain.auth.service;

import com.example.letmovie.domain.auth.security.JwtProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {

    private final JwtProperties jwtProperties;

    @Value("${cookie.secure}")
    private boolean isCookieSecure;

    /**
     * AccessToken에 대한 쿠키 생성
     */
    public void setAccessTokenCookie(HttpServletResponse response, String accessToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        configureCookie(accessTokenCookie, jwtProperties.getAccessTokenValidity());
        response.addCookie(accessTokenCookie);
    }

    /**
     * RefreshToken에 대한 쿠키 생성
     */
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        configureCookie(refreshTokenCookie, jwtProperties.getRefreshTokenValidity());
        response.addCookie(refreshTokenCookie);
    }

    /**
     * 기본 쿠키 설정
     */
    public void configureCookie(Cookie cookie, int maxAge) {
        cookie.setHttpOnly(true);
        cookie.setSecure(isCookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
    }

    /**
     * 쿠키 삭제
     */
    public void clearCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(isCookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
