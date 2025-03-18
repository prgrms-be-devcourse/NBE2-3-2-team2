package com.example.letmovie.domain.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class DailyPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int month;
    private int day;
    private int totalAmount;
    private Long totalCount;
    private LocalDateTime createdAt;

    @Builder
    public DailyPayment(Long id, int year, int month, int day, int totalAmount, Long totalCount) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.totalCount = totalCount;
        this.createdAt = LocalDateTime.now();
    }
}
