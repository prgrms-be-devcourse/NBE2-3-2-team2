package com.example.letmovie.domain.payment.repository;

import com.example.letmovie.domain.payment.entity.Payment;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory,Long> {

    Optional<PaymentHistory> findByPaymentAndPaymentStatus(Payment payment, PaymentStatus status);


    Page<PaymentHistory> findByPaymentStatus(PaymentStatus status, Pageable pageable);

}
