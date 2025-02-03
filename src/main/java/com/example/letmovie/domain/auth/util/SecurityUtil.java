package com.example.letmovie.domain.auth.util;

import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private static MemberRepository memberRepository;

    // setter method
    public static void setMemberRepository(MemberRepository memberRepository) {
        SecurityUtil.memberRepository = memberRepository;
    }

    /**
     *  현재 인증된 멤버 정보를 가져옴
     */
    public static Optional<Member> getCurrentMember() {

        String email = getCurrentMemberEmail();

        return memberRepository.findByEmail(email);
    }

    /**
     *  현재 인증된 사용자의 이메일을 가져옴
     */
    public static String getCurrentMemberEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }

        return null;
    }

    /**
     *  현재 인증된 사용자의 역할을 가져옴 (ROLE_USER, ROLE_ADMIN)
     */
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }

    /**
     *  현재 인증 정보를 가져옴
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     *  인증 정보 설정 (테스트 용)
     */
    public static void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
