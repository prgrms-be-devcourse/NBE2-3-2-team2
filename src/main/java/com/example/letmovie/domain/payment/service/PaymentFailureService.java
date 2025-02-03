package com.example.letmovie.domain.payment.service;

import com.example.letmovie.domain.payment.entity.Payment;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import com.example.letmovie.domain.payment.entity.PaymentStatus;
import com.example.letmovie.domain.payment.repository.PaymentHistoryRepository;
import com.example.letmovie.domain.payment.repository.PaymentRepository;
import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.payment.PaymentException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentFailureService {
    
    private final PaymentRepository paymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlePaymentFailure(Long reservationId, Exception e) {
            Payment payment = paymentRepository.findByReservationId(reservationId)
                    .orElseThrow(() -> new PaymentException(ErrorCodes.PAYMENT_NOT_FOUND));


            payment.updateStatus(PaymentStatus.PAYMENT_FAILED);
            paymentRepository.save(payment);

            // 에러 응답 파싱
            String errorCode = "";
            String errorMessage = "";

            if (e instanceof HttpClientErrorException) {
                String errorResponse = ((HttpClientErrorException) e).getResponseBodyAsString();
                try {
                    String cleanResponse = errorResponse.replaceAll("^\"|\"$", "");
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(cleanResponse);
                    errorCode = String.valueOf(jsonNode.get("error_code").asInt());
                    errorMessage = jsonNode.get("error_message").asText();

                } catch (JsonProcessingException ex) {
                    errorMessage = e.getMessage();
                }
            } else {
                errorMessage = e.getMessage();
            }

            PaymentHistory failureHistory = PaymentHistory.toFailureHistory(
                    payment,
                    errorMessage,
                    errorCode
            );
            paymentHistoryRepository.save(failureHistory);

        }
    }
