package com.example.letmovie.domain.reservation.repository;

import com.example.letmovie.domain.member.dto.response.ReservationDetailsDTO;
import com.example.letmovie.domain.member.dto.response.SeatDTO;
import com.example.letmovie.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT new com.example.letmovie.domain.member.dto.response.ReservationDetailsDTO( " +
            "r.id, r.status, mv.movieName, mv.posterImageUrl, t.theaterName, sc.screenName, " +
            "r.totalSeats, p.paidAt, s.showtimeDate, s.showtimeTime) " +
            "FROM Reservation r " +
            "JOIN r.showTime s " +
            "JOIN s.movie mv " +
            "JOIN s.screen sc " +
            "JOIN sc.theater t " +
            "JOIN Payment p ON p.reservation.id = r.id " +
            "WHERE r.member.id = :memberId " +
            "ORDER BY s.showtimeDate, s.showtimeTime DESC")
    List<ReservationDetailsDTO> findReservationsByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT new com.example.letmovie.domain.member.dto.response.SeatDTO( " +
            "st.id, st.seatLow, st.seatCol) " +
            "FROM Reservation r " +
            "JOIN r.reservationSeats rs " +
            "JOIN rs.seat st " +
            "WHERE r.id = :reservationId")
    List<SeatDTO> findSeatsByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.member " +
            "JOIN FETCH r.showTime st " +
            "JOIN FETCH st.movie " +
            "JOIN FETCH st.screen " +
            "WHERE r.id = :id")
    Optional<Reservation> findReservationWithMemberShowTimeAndMovie(@Param("id") Long id);
}
