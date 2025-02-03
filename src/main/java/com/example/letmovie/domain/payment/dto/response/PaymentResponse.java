package com.example.letmovie.domain.payment.dto.response;

import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.payment.entity.Payment;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;
import com.example.letmovie.domain.reservation.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class PaymentResponse {

    public record Ready(
            String tid,
            String cid,
            String next_redirect_pc_url,
            String next_redirect_mobile_url,
            String next_redirect_app_url,
            LocalDateTime created_at
    ) {
    }

    public record Success(
            String aid,
            String tid,
            String cid,
            String partner_order_id,
            String partner_user_id,
            String payment_method_type,
            String item_name,

            int quantity,
            Amount amount,            // Amount 객체로 변경
            LocalDateTime created_at,
            LocalDateTime approved_at
    ) {
        public record Amount(
                Integer total,
                Integer tax_free,
                Integer vat,
                Integer point,
                Integer discount
        ) {
            public static Success from(PaymentHistory paymentHistory) {
                return new Success(
                        paymentHistory.getAid(),
                        paymentHistory.getTid(),
                        paymentHistory.getCid(),
                        paymentHistory.getPartnerOrderId(),
                        paymentHistory.getPartnerUserId(),
                        paymentHistory.getPaymentMethodType(),
                        paymentHistory.getItemName(),
                        paymentHistory.getQuantity(),
                        new Amount(        // Amount 객체 생성
                                paymentHistory.getAmount(),
                                0,  // tax_free
                                null,  // vat
                                0,  // point
                                0   // discount
                        ),
                        paymentHistory.getCreatedAt(),
                        paymentHistory.getApprovedAt()
                );
            }
        }
    }

    public record Cancel(
            String aid,
            String tid,
            String cid,
            String status,
            String partner_order_id,
            String partner_user_id,
            String payment_method_type,
            String item_name,
            String item_code,
            Integer quantity,
            ApprovedCancelAmount approved_cancel_amount,  // 이번 요청으로 취소된 금액
            LocalDateTime created_at,
            LocalDateTime approved_at,
            LocalDateTime canceled_at
    ) {
        public record ApprovedCancelAmount(
                Integer total,
                Integer tax_free,
                Integer vat,
                Integer point,          // 이번 요청으로 취소된 사용한 포인트 금액
                Integer discount,       // 이번 요청으로 취소된 할인 금액
                Integer green_deposit
        ) {}
    }

    /* Payment -> PaymentDTO 가져오기.*/
    public record Get(
            Long paymentId,
            Long reservationId,
            Long memberId,
            int amount,
            String nickName,
            PaymentStatus paymentStatus,
            LocalDateTime paidAt
    ) {
        public static Get from(Payment payment) {
            return new Get(
                    payment.getId(),
                    payment.getReservation().getId(),
                    payment.getMember().getId(),
                    payment.getAmount(),
                    payment.getReservation().getShowTime().getMovie().getMovieName(),    // Member 엔티티의 getName() 메서드 사용
                    payment.getPaymentStatus(),
                    payment.getPaidAt()
            );
        }
    }


}