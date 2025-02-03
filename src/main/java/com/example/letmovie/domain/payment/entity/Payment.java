package com.example.letmovie.domain.payment.entity;

import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id",nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private int amount;

    @Column
    private String paymentMethodType;  // 결제 수단

    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Builder
    public Payment(Member member, Reservation reservation,PaymentStatus paymentStatus, int amount) {
        this.member = member;
        this.reservation = reservation;
        this.amount = amount;
        this.paidAt = LocalDateTime.now();
        updateStatus(paymentStatus);
    }

    public void updatePaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
