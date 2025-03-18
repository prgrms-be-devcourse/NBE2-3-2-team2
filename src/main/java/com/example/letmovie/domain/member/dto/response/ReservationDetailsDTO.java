package com.example.letmovie.domain.member.dto.response;

import com.example.letmovie.domain.reservation.entity.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ReservationDetailsDTO {
    private final Long reservationId;                     // 예매 ID
    private final ReservationStatus reservationStatus;    // 결제상태
    private final String reservationStatusDisplayName;    // 결제상태 (한글 표시값)
    private final String movieName;                       // 영화 이름
    private final String posterUrl;                       // 포스터 이미지 URL
    private final String theaterName;                     // 극장 이름
    private final String screenName;                      // 관 이름
    private final int totalSeats;                         // 예매한 총 좌석 수
    private final LocalDateTime paidAt;                   // 결제일시
    private final LocalDate showtimeDate;                 // 상영 날짜
    private final LocalTime showtimeTime;                 // 상영 시간
    private final String formattedPaymentAt;              // 변환된 결제일시
    @Setter
    private List<SeatDTO> seats;                    // 좌석

    public ReservationDetailsDTO(Long reservationId, ReservationStatus reservationStatus, String movieName,
                                 String posterUrl, String theaterName, String screenName, int totalSeats,
                                 LocalDateTime paidAt, LocalDate showtimeDate, LocalTime showtimeTime,
                                 Long seatId, int seatRow, int seatCol) {
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.reservationStatusDisplayName = reservationStatus.getDisplayName();
        this.movieName = movieName;
        this.posterUrl = posterUrl;
        this.theaterName = theaterName;
        this.screenName = screenName;
        this.totalSeats = totalSeats;
        this.paidAt = paidAt;
        this.showtimeDate = showtimeDate;
        this.showtimeTime = showtimeTime;
        this.formattedPaymentAt = paidAt.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

        this.seats = new ArrayList<>();
        this.seats.add(new SeatDTO(seatId, seatRow, seatCol));
    }

    public void addSeat(SeatDTO seat) {
        this.seats.add(seat);
    }
}
