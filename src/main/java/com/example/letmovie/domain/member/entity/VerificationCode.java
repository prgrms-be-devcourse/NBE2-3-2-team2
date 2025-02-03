package com.example.letmovie.domain.member.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int code;

    @Column(nullable = false)
    private int requestCount; // 하루 이메일 인증 요청 수

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public VerificationCode() {
    }

    public VerificationCode(String email, int code) {
        this.email = email;
        this.code = code;
        this.requestCount = 1;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired(int expirationMinutes) {
        return createdAt.plusMinutes(expirationMinutes).isBefore(LocalDateTime.now());
    }

    public void incrementRequestCount() {
        this.requestCount++;
    }

    public boolean canRequestMore(int maxRequestsPerDay) {
        return requestCount < maxRequestsPerDay;
    }
}