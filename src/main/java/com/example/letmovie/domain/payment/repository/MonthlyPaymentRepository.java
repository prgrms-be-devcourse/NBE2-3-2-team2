package com.example.letmovie.domain.payment.repository;

import com.example.letmovie.domain.payment.entity.DailyPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPaymentRepository extends JpaRepository<DailyPayment, Long> {
}