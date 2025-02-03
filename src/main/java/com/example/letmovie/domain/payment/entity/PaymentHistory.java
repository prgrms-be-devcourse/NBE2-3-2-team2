package com.example.letmovie.domain.payment.entity;

import com.example.letmovie.domain.payment.dto.response.PaymentResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentHistory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_history_id")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private String aid;          // 요청 고유 번호
    private String tid;         // 결제 고유 번호
    private String cid;          // 가맹점 코드

    @Column(name = "partner_order_id")
    private String partnerOrderId;  // 가맹점 주문번호

    @Column(name = "partner_user_id")
    private String partnerUserId;   // 가맹점 회원 id

    @Column(name = "payment_method_type")
    private String paymentMethodType;  // 결제 수단

    @Column(name = "item_name")
    private String itemName;        // 상품명

    private int quantity;
    // 수량
    private int amount;             // 총 결제 금액

    private String errorMessage;
    private String errorCode;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;  // 결제 요청 시각

    @Column(name = "approved_at")
    private LocalDateTime approvedAt; // 결제 승인 시각

    @Builder
    private PaymentHistory(Payment payment, String aid, String tid, String cid,
                          String partnerOrderId, String partnerUserId,
                          String paymentMethodType, String itemName,
                          int quantity, int amount,
                          LocalDateTime createdAt,
                          LocalDateTime approvedAt,String errorCode,String errorMessage,PaymentStatus paymentStatus) {
        this.payment = payment;
        this.aid = aid;
        this.tid = tid;
        this.cid = cid;
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.paymentMethodType = paymentMethodType;
        this.itemName = itemName;
        this.quantity = quantity;
        this.amount = amount;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
        this.errorCode = errorCode;        // 추가
        this.errorMessage = errorMessage;  // 추가
        this.paymentStatus = paymentStatus; // 추가
    }


//     결제 성공 시 PaymentHistory 생성 메서드
    public static PaymentHistory toPaymentHistory(Payment payment, PaymentResponse.Success response) {
        return PaymentHistory.builder()
                .payment(payment)
                .aid(response.aid())
                .tid(response.tid())
                .cid(response.cid())
                .partnerOrderId(response.partner_order_id())
                .partnerUserId(response.partner_user_id())
                .paymentMethodType(response.payment_method_type())
                .itemName(response.item_name())
                .quantity(response.quantity())
                .amount(response.amount().total())  // Amount 객체에서 total 값을 가져옴
                .createdAt(response.created_at())
                .approvedAt(response.approved_at())
                .paymentStatus(PaymentStatus.PAYMENT_SUCCESS)
                .build();
    }
    // 실패 케이스
    public static PaymentHistory toFailureHistory(Payment payment, String errorMessage, String errorCode) {
        return PaymentHistory.builder()
                .payment(payment)
                .paymentStatus(payment.getPaymentStatus())
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .createdAt(LocalDateTime.now())
                .build();
    }

    // PaymentHistory.java
    public static PaymentHistory toCancelHistory(PaymentHistory paymentHistory, PaymentResponse.Cancel response) {
        return PaymentHistory.builder()
                .payment(paymentHistory.getPayment())
                .aid(response.aid())
                .tid(response.tid())
                .cid(response.cid())
                .partnerOrderId(response.partner_order_id())
                .partnerUserId(response.partner_user_id())
                .paymentMethodType(response.payment_method_type())
                .itemName(response.item_name())
                .quantity(response.quantity())
                .amount(response.approved_cancel_amount().total())  // 취소된 금액
                .paymentStatus(PaymentStatus.valueOf(response.status()))  // 취소 상태로 변경
                .createdAt(response.created_at())
                .approvedAt(response.approved_at())
                .build();
    }

    public static PaymentHistory toExpirationHistory(
            Payment payment,
            String message,
            String code) {
        return PaymentHistory.builder()
                .payment(payment)
                .paymentStatus(PaymentStatus.PAYMENT_CANCELLED)
                .errorMessage(message)
                .errorCode(code)
                .build();
    }
}
