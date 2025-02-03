package com.example.letmovie.domain.payment.provider;

import com.example.letmovie.domain.payment.dto.request.PaymentRequest;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class PaymentParamProvider {
    // 결제 준비 파라미터 생성
    public Map<String, String> createReadyParams(PaymentRequest.Info request, String movieName) {

        Map<String, String> params = new HashMap<>();
        params.put("cid", "TC0ONETIME");
        params.put("partner_order_id", String.valueOf(request.reservation_id()));
        params.put("partner_user_id", String.valueOf(request.member_id())); // 주문 멤버id
        params.put("item_name", movieName); // 영화 이름
        params.put("quantity", "1"); // 티켓 수량
        params.put("total_amount", String.valueOf(request.totalPrice())); //전체 가격
        params.put("tax_free_amount", "0");
        params.put("approval_url", "http://localhost:8080/payment/success");
        params.put("cancel_url", "http://localhost:8080/payment/cancel");
        params.put("fail_url", "http://localhost:8080/payment/fail");

        return params;
    }

    public Map<String, String> createApprovalParams(String pgToken, String tid, String partnerUserId,
                                                    String partnerOrderId, String cid) {
        Map<String, String> params = new HashMap<>();
        params.put("cid", cid);
        params.put("tid", tid);
        params.put("pg_token", pgToken); // 오류시 테스트
        params.put("partner_order_id", partnerOrderId);
        params.put("partner_user_id", partnerUserId);

        return params;
    }

    public Map<String, String> createCancelParams(PaymentHistory paymentHistory) {
        Map<String, String> params = new HashMap<>();
        params.put("cid", paymentHistory.getCid());
        params.put("tid", paymentHistory.getTid());
        params.put("cancel_amount", String.valueOf(paymentHistory.getAmount()));
        params.put("cancel_tax_free_amount", "0");        // 취소 비과세 금액
        params.put("cancel_vat_amount", "0");             // 취소 부가세 금액

        return params;
    }
}
