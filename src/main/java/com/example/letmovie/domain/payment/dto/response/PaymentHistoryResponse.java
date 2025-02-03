package com.example.letmovie.domain.payment.dto.response;

import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentHistoryResponse {

        public record Info(
                Long payment_history_id,
                Long payment_id,
                String tid,
                Long member_id,
                Long reservation_id,
                String item_name,
                Integer amount,
                String payment_method_type,
                PaymentStatus payment_status,
                LocalDateTime created_at,
                LocalDateTime approved_at
        ) {
            public static Info from(PaymentHistory paymentHistory) {
                return new Info(
                        paymentHistory.getId(),
                        paymentHistory.getPayment().getId(),
                        paymentHistory.getTid(),
                        paymentHistory.getPayment().getMember().getId(),
                        paymentHistory.getPayment().getReservation().getId(),
                        paymentHistory.getItemName(),
                        paymentHistory.getAmount(),
                        paymentHistory.getPaymentMethodType(),
                        paymentHistory.getPayment().getPaymentStatus(),
                        paymentHistory.getCreatedAt(),
                        paymentHistory.getApprovedAt()
                );
            }
        }
    }
