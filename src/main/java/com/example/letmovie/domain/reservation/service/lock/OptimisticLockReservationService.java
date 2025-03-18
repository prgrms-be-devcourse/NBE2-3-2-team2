package com.example.letmovie.domain.reservation.service.lock;

import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.member.repository.MemberRepository;
import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.reservation.dto.response.ReservationResponseDTO;
import com.example.letmovie.domain.reservation.entity.Reservation;
import com.example.letmovie.domain.reservation.entity.ReservationSeat;
import com.example.letmovie.domain.reservation.entity.ReservationStatus;
import com.example.letmovie.domain.reservation.entity.Seat;
import com.example.letmovie.domain.reservation.repository.ReservationRepository;
import com.example.letmovie.domain.reservation.repository.SeatRepository;
import com.example.letmovie.domain.reservation.repository.ShowtimeRepository;
import com.example.letmovie.global.exception.exceptionClass.auth.MemberNotFoundException;
import com.example.letmovie.global.exception.exceptionClass.reservation.SeatNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptimisticLockReservationService {
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;
    private final ShowtimeRepository showtimeRepository;


    @Transactional
    public ReservationResponseDTO reservation(List<String> seatList, Long memberId, Long showtimeId) {
        Showtime showtime = showtimeRepository.findByIdWithOptimisticLock(showtimeId).orElseThrow(SeatNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        // 먼저 Reservation 생성
        Reservation reservation = Reservation.builder()
                .showTime(showtime)
                .member(member)
                .status(ReservationStatus.PENDING)
                .reservationDate(LocalDateTime.now())
                .totalSeats(seatList.size())
                .build();

        // Reservation을 먼저 저장
        reservationRepository.save(reservation);

        List<ReservationSeat> reservationSeats = seatList.stream().map(seat -> {
            String[] split = seat.split("-");
            int row = Integer.parseInt(split[0]);
            int col = Integer.parseInt(split[1]);

            Long screenId = showtime.getScreen().getId();
            Seat seatEntity = seatRepository.findByColAndRowAndScreenId(col, row, screenId)
                    .orElseThrow(SeatNotFoundException::new);

            if(!seatEntity.isAble()) {
                char rowLabel = (char) ('A' + row - 1);
                throw new SeatNotFoundException("좌석 " + rowLabel  + "-" + col + "는 이미 선택된 좌석입니다.");
            }

            ReservationSeat reservationSeat = ReservationSeat.createReservationSeat(seatEntity, showtime);
            reservation.addReservationSeat(reservationSeat);

            return reservationSeat;
        }).collect(Collectors.toList());

        reservation.updateTotalPrice(reservationSeats.stream().mapToInt(ReservationSeat::getSeatPrice).sum());

        return new ReservationResponseDTO(
                reservation.getId(),
                memberId,
                member.getNickname(),
                reservation.getTotalPrice());
    }
}