package com.example.letmovie.domain.auth.repository;

import com.example.letmovie.domain.auth.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    Optional<TokenBlacklist> findByRefreshToken(String refreshToken);
    void deleteByExpiryDateBefore(LocalDateTime now); // 만료된 토큰 삭제
}