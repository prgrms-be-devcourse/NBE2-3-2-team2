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
public class MonthlyPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int month;
    private int day;
    private Long totalCount;
    private LocalDateTime createdAt;

    @Builder
    public MonthlyPayment(Long id, int year, int month, int day, Long totalCount) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.totalCount = totalCount;
        this.createdAt = LocalDateTime.now();
    }
}
