package com.example.letmovie.domain.auth.service;

import com.example.letmovie.domain.auth.security.JwtTokenProvider;
import com.example.letmovie.domain.member.dto.request.LoginRequestDTO;
import com.example.letmovie.domain.member.dto.response.LoginResponseDTO;
import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    /**
     *  Refresh Token을 블랙리스트에 추가
     */
    public void logout(String refreshToken, LocalDateTime expiryDate) {
        tokenBlacklistService.addToBlacklist(refreshToken, expiryDate);
    }
}
