package com.example.letmovie.domain.payment.service;

import com.example.letmovie.domain.payment.dto.response.PaymentHistoryResponse;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.repository.PaymentHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;


    public Page<PaymentHistoryResponse.Info> getAllPaymentHistory(Pageable pageable) {
        Page<PaymentHistory> paymentHistories = paymentHistoryRepository.findAll(pageable);
        return paymentHistories.map(PaymentHistoryResponse.Info::from);
    }


}
