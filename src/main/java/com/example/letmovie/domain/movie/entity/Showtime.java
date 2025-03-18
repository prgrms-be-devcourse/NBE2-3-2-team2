package com.example.letmovie.domain.movie.entity;

import com.example.letmovie.domain.reservation.entity.Screen;
import com.example.letmovie.global.exception.exceptionClass.reservation.ExceedTotalSeatsException;
import com.example.letmovie.global.exception.exceptionClass.reservation.InsufficientSeatsException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="screen_id")
    private Screen screen;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @NotNull
    @Column(columnDefinition = "Date")
    private LocalDate showtimeDate;

    @NotNull
    private LocalTime showtimeTime;

    @NotNull
    private int totalSeats;

    @NotNull
    private int remainingSeats;

    @Version
    private int version;

    /**
     *  좌석 감소 로직.
     */
    public void removeSeats(int lose){
        int restSeats = remainingSeats-lose;
        if(restSeats < 0){
            throw new ExceedTotalSeatsException();
        }
        remainingSeats = restSeats;
    }

    /**
     * 취소시 남은 좌석 증가
     */
    public void addSeats(int add){
        if (this.remainingSeats + add > this.totalSeats) {
            throw new InsufficientSeatsException();
        }
        remainingSeats += add;
    }

    @Builder
    public Showtime(Screen screen, Movie movie, LocalDate showtimeDate, LocalTime showtimeTime, int totalSeats, int remainingSeats) {
        this.screen = screen;
        this.movie = movie;
        this.showtimeDate = showtimeDate;
        this.showtimeTime = showtimeTime;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
    }
}