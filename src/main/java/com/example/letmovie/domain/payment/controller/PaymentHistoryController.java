package com.example.letmovie.domain.payment.controller;

import com.example.letmovie.domain.payment.dto.response.PaymentHistoryResponse;
import com.example.letmovie.domain.payment.service.PaymentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-histories")
@RequiredArgsConstructor
@Tag(name = " 결제 내역 API")
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;


    @GetMapping
    @Operation(summary = "관리자 모든 결제 내역 상세 조회")
    public ResponseEntity<Page<PaymentHistoryResponse.Info>> getAllPaymentHistory(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok()
                .body(paymentHistoryService.getAllPaymentHistory(pageable));
    }

    


}
