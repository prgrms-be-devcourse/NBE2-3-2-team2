package com.example.letmovie.domain.auth.service;

import com.example.letmovie.domain.auth.entity.TokenBlacklist;
import com.example.letmovie.domain.auth.repository.TokenBlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final TokenBlacklistRepository tokenBlacklistRepository;

    /**
     *  Refresh Token이 블랙리스트에 존재하는지 확인
     */
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.findByRefreshToken(token).isPresent();
    }

    /**
     *  로그아웃 시 리프레시 토큰 블랙리스트에 추가
     */
    public void addToBlacklist(String token, LocalDateTime expiryDate) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist(token, expiryDate);
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    /**
     *  매일 새벽 2시에 만료된 토큰 삭제
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredTokens() {
        tokenBlacklistRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
