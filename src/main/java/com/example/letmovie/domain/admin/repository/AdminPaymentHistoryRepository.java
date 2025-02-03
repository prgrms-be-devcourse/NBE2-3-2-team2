package com.example.letmovie.domain.admin.repository;

import com.example.letmovie.domain.payment.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminPaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByPartnerUserId(String partnerUserId);
}
