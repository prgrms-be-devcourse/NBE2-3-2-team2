package com.example.letmovie.domain.payment.scheduler;

import com.example.letmovie.domain.payment.entity.Payment;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;
import com.example.letmovie.domain.payment.repository.PaymentHistoryRepository;
import com.example.letmovie.domain.payment.repository.PaymentRepository;
import com.example.letmovie.domain.reservation.entity.Reservation;
import com.example.letmovie.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentScheduler {

    private final PaymentRepository paymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final ReservationRepository reservationRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkExpiredPayments() {
        LocalDateTime expirationCutoff = LocalDateTime.now().minusMinutes(14);
        log.info("결제 만료전 체크: {}", expirationCutoff);

        List<Payment> expiredPayments = paymentRepository.findByPaymentStatusAndPaidAtBefore(
                PaymentStatus.AWAITING_PAYMENT,
                expirationCutoff
        );

        for (Payment payment : expiredPayments) {
            try {
                handleExpiredPayment(payment);
            } catch (Exception e) {
                log.error("결제에 대한 예약 아이디 조회 실패 {}", payment.getReservation().getId(), e);
            }
        }
    }

    private void handleExpiredPayment(Payment payment) {

        payment.updateStatus(PaymentStatus.PAYMENT_FAILED);
        paymentRepository.save(payment);

        Reservation reservation = payment.getReservation();
        reservation.cancelReservation();
        reservationRepository.save(reservation);

        PaymentHistory expirationHistory = PaymentHistory.toExpirationHistory(
                payment,
                "결제 요청 15분 만료",
                "EXPIRED"
        );
        paymentHistoryRepository.save(expirationHistory);
        log.info("결제 만료 - PaymentId: {}, ReservationId: {}",
                payment.getId(), reservation.getId());
    }
}
