package com.example.letmovie.domain.auth.security;

import com.example.letmovie.domain.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${excluded.paths.equals:}")
    private String equalsPaths;

    @Value("${excluded.paths.startswith:}")
    private String startsWithPaths;

    private Set<String> equalsPathsSet;
    private Set<String> startsWithPathsSet;

    @PostConstruct
    private void initializePathSets() {
        equalsPathsSet = Arrays.stream(equalsPaths.split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet());

        startsWithPathsSet = Arrays.stream(startsWithPaths.split(","))
                        .map(String::trim) // 문자열 앞뒤 공백 제거
                        .collect(Collectors.toSet());
    }

    /**
     * FilterChain 적용 제외
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        // 정확히 일치하는 경로 확인
        if (equalsPathsSet.contains(path)) {
            return true;
        }

        // 특정 문자열로 시작하는 경로 확인
        for (String prefix : startsWithPathsSet) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * JWT 토큰 인증 정보 확인
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 인증이 필요하지 않은 경로는 필터를 건너뜀
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response); // 필터 체인 계속 진행
            return;
        }

        String token = resolveToken(request);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
               authenticateUser(token, request);
            }
            else {
                log.warn("유효하지 않은 JWT 토큰 요청 URI: {}", request.getRequestURI());
                handleInvalidToken(response);
                return;
            }
        } catch (ExpiredJwtException e) {
            log.warn("Access Token 만료: {}", e.getMessage());
            handleExpiredToken(isAjaxRequest(request), response);
            return; // 필터 체인 중단
        } catch (Exception e) {
            log.error("JWT 인증 실패: {}", e.getMessage());
            handleAuthenticationError(isAjaxRequest(request), response);
            return;
        }
        filterChain.doFilter(request, response); // 다음 필터로 요청 전달
    }

    /**
     * 쿠키에서 JWT 추출
     */
    private String resolveToken(HttpServletRequest request) {
        // 쿠키 배열에서 accessToken 찾기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    log.debug("Access Token 추출: {}", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        // 쿠키에 accessToken이 없으면 null 반환
        log.warn("Access Token 이 쿠키에서 발견되지 않음");
        return null;
    }

    /**
     * 토큰에 등록된 사용자를 추출해 SecurityContext에 설정
     */
    private void authenticateUser(String token, HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromToken(token); // 토큰에서 사용자 이메일 추출
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email); // 사용자 정보 로드

        // 인증된 사용자 정보를 SecurityContext에 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.info("SecurityContext 인증 정보: {}, 권한: {}", email, userDetails.getAuthorities());

        // 요청 속성에 사용자 이메일 설정
        request.setAttribute("userEmail", email);
    }

    /**
     * AJAX 요청인지 확인
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith); // AJAX 요청은 X-Requested-With 헤더를 포함
    }

    /**
     * AJAX 요청일 시 에러 메시지 처리
     */
    private void respondWithJson(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized: Invalid JWT Token\"}");
    }

    private void handleInvalidToken(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login"); // 로그인 페이지로 리디렉션
    }

    private void handleExpiredToken(boolean isAjax, HttpServletResponse response) throws IOException {
        if (isAjax) {
            respondWithJson(response);
        } else {
            response.sendRedirect("/token/refresh"); // 토큰 갱신 페이지로 리디렉션
        }
    }

    private void handleAuthenticationError(boolean isAjax, HttpServletResponse response) throws IOException {
        if (isAjax) {
            respondWithJson(response);
        } else {
            response.sendRedirect("/login"); // 로그인 페이지로 리디렉션
        }
    }
}