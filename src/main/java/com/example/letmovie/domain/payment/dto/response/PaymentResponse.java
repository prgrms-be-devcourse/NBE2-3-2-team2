package com.example.letmovie.domain.payment.dto.response;

import com.example.letmovie.domain.payment.entity.PaymentHistory;

import java.time.LocalDateTime;

public class PaymentResponse {

    public record Ready(
            String tid,
            String cid,
            String next_redirect_pc_url,
            String next_redirect_mobile_url,
            String next_redirect_app_url,
            LocalDateTime created_at
    ){}

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

        }

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
