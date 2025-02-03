package com.example.letmovie.domain.reservation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Seat {

    @Id
    @Column(name ="seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @OneToMany(mappedBy = "seat")
    private List<ReservationSeat> reservationSeats = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @NotNull
    private int seatLow;

    @NotNull
    private int seatCol;

    private boolean isAble;

    @NotNull
    private int price;

    @Version
    private int version;


    /**
     * 연관관계 메서드
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
        screen.getSeats().add(this);
    }

    public void setAble(boolean able) {
        isAble = able;
    }

    public void setSeatType(@NotNull SeatType seatType) {
        this.seatType = seatType;
    }

    public void setPrice(@NotNull int price) {
        this.price = price;
    }

    @Builder
    public Seat(Screen screen, SeatType seatType, int seatLow, int seatCol, int price) {
        this.screen = screen;
        this.seatType = seatType;
        this.seatLow = seatLow;
        this.seatCol = seatCol;
        this.isAble = true;
        this.price = price;
    }
}
