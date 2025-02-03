package com.example.letmovie.domain.reservation.entity;

import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.global.exception.exceptionClass.reservation.ReservationCancelException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Reservation_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showTime_id")
    private Showtime showTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL) //양방향 매핑
    private List<ReservationSeat> reservationSeats = new ArrayList<>();

    @NotNull
    private LocalDateTime reservationDate;

    @NotNull
    private int totalSeats;

    @NotNull
    private int totalPrice;

    /**
     *  연관관계 메서드 - 양방향 설정
     */
    public void addReservationSeat(ReservationSeat reservationSeat){
        reservationSeats.add(reservationSeat);
        reservationSeat.setReservation(this);
    }

    public void updateTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     *  생성 메서드 - 정적 팩토리 메서드 -> // notnull 정합성 실패로 로직 변경
     */
    public static Reservation createReservation(Member member, Showtime showtime,List<ReservationSeat> reservationSeats){
        Reservation reservation = Reservation.builder()
                .showTime(showtime)
                .member(member)
                .status(ReservationStatus.PENDING)
                .reservationDate(LocalDateTime.now())
                .totalSeats(reservationSeats.size())
                .totalPrice(reservationSeats.stream().mapToInt(ReservationSeat::getSeatPrice).sum())
                .build();

        for (ReservationSeat reservationSeat : reservationSeats) {
            reservation.addReservationSeat(reservationSeat); //양방향 설정
        }
        return reservation;
    }

    /**
     * 예매 취소 로직.
     */
    public void cancelReservation() {
        if(status.equals(ReservationStatus.VIEWED)){
            throw new ReservationCancelException();
        }

        this.status = ReservationStatus.CANCELLED;

        for (ReservationSeat reservationSeat : reservationSeats) {
            reservationSeat.cancel(this.getShowTime()); // 예매좌석->좌석에서 가능여부 true, 상영관 총 좌석 수 증가.
        }
    }

    public void setStatus(@NotNull ReservationStatus reservationStatus) {
        this.status = reservationStatus;
    }

    @Builder
    public Reservation(Showtime showTime, Member member, ReservationStatus status, LocalDateTime reservationDate, int totalSeats, int totalPrice) {
        this.showTime = showTime;
        this.member = member;
        this.status = status;
        this.reservationDate = reservationDate;
        this.totalSeats = totalSeats;
        this.totalPrice = totalPrice;
    }
}
