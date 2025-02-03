package com.example.letmovie.domain.reservation.repository;

import com.example.letmovie.domain.member.dto.response.ReservationDetailsDTO;
import com.example.letmovie.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT new com.example.letmovie.domain.member.dto.response.ReservationDetailsDTO( " +
            "r.id, r.status, mv.movieName, mv.posterImageUrl, t.theaterName, sc.screenName, " +
            "r.totalSeats, p.paidAt, s.showtimeDate, s.showtimeTime, " +
            "st.id, st.seatLow, st.seatCol) " +
            "FROM Reservation r " +
            "JOIN r.showTime s " +
            "JOIN s.movie mv " +
            "JOIN s.screen sc " +
            "JOIN sc.theater t " +
            "JOIN Payment p ON p.reservation.id = r.id " +
            "JOIN ReservationSeat rs ON rs.reservation.id = r.id " +
            "JOIN rs.seat st " +
            "WHERE r.member.id = :memberId " +
            "ORDER BY s.showtimeDate DESC, s.showtimeTime DESC")
    List<ReservationDetailsDTO> findReservationsWithSeats(@Param("memberId") Long memberId);
}
