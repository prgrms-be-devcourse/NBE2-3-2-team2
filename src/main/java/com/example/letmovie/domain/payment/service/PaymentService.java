package com.example.letmovie.domain.payment.service;

import com.example.letmovie.domain.member.repository.MemberRepository;
import com.example.letmovie.domain.payment.dto.request.PaymentRequest;
import com.example.letmovie.domain.payment.dto.response.PaymentResponse;
import com.example.letmovie.domain.payment.entity.Payment;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;
import com.example.letmovie.domain.payment.provider.PaymentParamProvider;
import com.example.letmovie.domain.payment.repository.PaymentHistoryRepository;
import com.example.letmovie.domain.payment.repository.PaymentRepository;
import com.example.letmovie.domain.payment.util.HttpRequestUtil;
import com.example.letmovie.domain.reservation.entity.Reservation;
import com.example.letmovie.domain.reservation.entity.ReservationStatus;
import com.example.letmovie.domain.reservation.repository.ReservationRepository;
import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.payment.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {

    private final HttpRequestUtil httpRequestUtil;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentFailureService paymentFailureService;
    private final PaymentParamProvider paymentParamProvider;

    @Value("${kakao.pay.host}")
    private String HOST;
    @Value("${kakao.pay.secret.key}")
    private String secretKey;

    @Transactional(readOnly = true)
    public PaymentResponse.Ready ready(PaymentRequest.Info request) {

        String movieName = initializePayment(request);
        HttpHeaders headers = httpRequestUtil.createHeaders(secretKey);
        Map<String, String> parameters = paymentParamProvider.createReadyParams(request,movieName);
        return httpRequestUtil.post(
                HOST+"/online/v1/payment/ready",
                parameters,
                headers,
                PaymentResponse.Ready.class);
    }

    public PaymentResponse.Success success(String pgToken, String tid, String cid, String partnerUserId, String partnerOrderId) {
        Long reservationId = Long.parseLong(partnerOrderId);

        try{
        HttpHeaders headers = httpRequestUtil.createHeaders(secretKey);
        Map<String, String> parameters = paymentParamProvider.createApprovalParams(pgToken, tid,partnerUserId, partnerOrderId,cid);

        PaymentResponse.Success response = httpRequestUtil.post(
                HOST + "/online/v1/payment/approve",
                parameters,
                headers,
                PaymentResponse.Success.class
        );

        initializePaymentAndPaymentHistory(reservationId, response);
        return response;
        } catch (Exception e){
            paymentFailureService.handlePaymentFailure(reservationId, e);
            throw new PaymentException(ErrorCodes.PAYMENT_FAILED);
        }
    }

    public PaymentResponse.Cancel cancel(Long paymentId) {
        Payment beforePayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException(ErrorCodes.PAYMENT_NOT_FOUND));

        PaymentHistory paymentHistory = paymentHistoryRepository
                .findByPaymentAndPaymentStatus(beforePayment, PaymentStatus.PAYMENT_SUCCESS)
                .orElseThrow(() -> new PaymentException(ErrorCodes.PAYMENT_NOT_FOUND));
        
            HttpHeaders headers = httpRequestUtil.createHeaders(secretKey);
            Map<String, String> parameters = paymentParamProvider.createCancelParams(paymentHistory);

            PaymentResponse.Cancel response = httpRequestUtil.post(
                    HOST + "/online/v1/payment/cancel",
                    parameters,
                    headers,
                    PaymentResponse.Cancel.class
            );
            Payment payment = paymentHistory.getPayment();
            payment.updateStatus(PaymentStatus.valueOf(response.status()));

            if (!payment.getReservation().getStatus().equals("CANCELLED")) {
                payment.getReservation().setStatus(ReservationStatus.CANCELLED);
            }

            PaymentHistory cancelHistory = PaymentHistory.toCancelHistory(paymentHistory, response);
            paymentHistoryRepository.save(cancelHistory);

            return response;
        
    }


    @Transactional
    public List<PaymentResponse.Get> getMemberPayment(Long memberId) {
        List<Payment> payments = paymentRepository.findByMemberId(memberId);
        return payments.stream()
                .map(PaymentResponse.Get::from)
                .collect(Collectors.toList());
    }

    private String initializePayment(PaymentRequest.Info request) {
        Payment payment = Payment.builder()
                .member(memberRepository.findById(request.member_id())
                        .orElseThrow(() -> new PaymentException(ErrorCodes.PAYMENT_NOT_FOUND)))
                .reservation(reservationRepository.findById(request.reservation_id())
                        .orElseThrow(() -> new PaymentException(ErrorCodes.PAYMENT_NOT_FOUND)))
                .amount(request.totalPrice())
                .paymentStatus(PaymentStatus.AWAITING_PAYMENT)
                .build();

        String name = payment.getReservation().getShowTime().getMovie().getMovieName();
        paymentRepository.save(payment);
        return name;
    }

    private void initializePaymentAndPaymentHistory(Long reservationId, PaymentResponse.Success response) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약 없음."));
        reservation.setStatus(ReservationStatus.COMPLETED);

        Payment payment = paymentRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new PaymentException(ErrorCodes.PAYMENT_NOT_FOUND));

        payment.updateStatus(PaymentStatus.PAYMENT_SUCCESS);

        PaymentHistory paymentHistory = PaymentHistory.toPaymentHistory(payment, response);

        payment.updatePaymentMethodType(paymentHistory.getPaymentMethodType());
        paymentHistoryRepository.save(paymentHistory);

    }


}
