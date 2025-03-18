package com.example.letmovie.domain.reservation.facade;

import com.example.letmovie.domain.reservation.dto.response.ReservationResponseDTO;
import com.example.letmovie.domain.reservation.service.lock.OptimisticLockReservationService;
import com.example.letmovie.global.exception.exceptionClass.reservation.SeatNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OptimisticLockReservationFacade {
    private final OptimisticLockReservationService optimisticLockReservationService;

    public ReservationResponseDTO reservation(List<String> seatList, Long memberId, Long showtimeId) throws InterruptedException {
        int maxRetries = 1;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                return optimisticLockReservationService.reservation(seatList, memberId, showtimeId);

            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new SeatNotFoundException(e.getMessage());
                }
                Thread.sleep(50); // 재시도 전 대기
            }
        }
        throw new SeatNotFoundException("[404] : 예약에 실패했습니다."); // 비정상 상황에 대한 처리
    }
}
