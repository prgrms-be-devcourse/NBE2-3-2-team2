package com.example.letmovie.domain.reservation.entity;

import com.example.letmovie.domain.movie.entity.Showtime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationSeat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_seat_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @NotNull
    private int seatPrice;

    /**
     *  연관관계 메서드
     */
    public void setSeat(Seat seat) {
        this.seat = seat;
        seat.getReservationSeats().add(this);
        seat.setAble(false);
    }

    public static ReservationSeat createReservationSeat(Seat seat, Showtime showtime) {
        ReservationSeat reservationSeat = ReservationSeat.builder()
                .seatPrice(seat.getPrice())
                .build();
        reservationSeat.setSeat(seat);

        showtime.removeSeats(1);

        return reservationSeat;
    }

    public void cancel(Showtime showtime){
        seat.setAble(true);//예매 가능 여부 허용
        showtime.addSeats(1);
    }

    /**
     * 연관관계 setter
     */
    public void setReservation(@NotNull Reservation reservation) {
        this.reservation = reservation;
    }


    @Builder
    public ReservationSeat(Reservation reservation, Seat seat, int seatPrice) {
        this.reservation = reservation;
        this.seat = seat;
        this.seatPrice = seatPrice;
    }
}
