package com.example.letmovie.domain.reservation.repository;

import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.reservation.entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT s " +
            "FROM Seat s " +
            "WHERE s.seatCol = :seatCol AND s.seatLow = :seatLow AND s.screen.id = :screenId")
    Optional<Seat> findByColAndRowAndScreenId(@Param("seatCol") int seatCol,
                                   @Param("seatLow") int seatLow,
                                   @Param("screenId") Long screenId);

    /**
     * 비관적 락 테스트
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s " +
            "FROM Seat s " +
            "WHERE s.seatCol = :seatCol AND s.seatLow = :seatLow AND s.screen.id = :screenId")
    Optional<Seat> findByIdWithPessimisticLock(@Param("seatCol") int seatCol,
                                              @Param("seatLow") int seatLow,
                                              @Param("screenId") Long screenId);

    /**
     * 낙관적 락 테스트
     */
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s " +
            "FROM Seat s " +
            "WHERE s.seatCol = :seatCol AND s.seatLow = :seatLow AND s.screen.id = :screenId")
    Optional<Seat> findByIdWithOptimisticLock(@Param("seatCol") int seatCol,
                                               @Param("seatLow") int seatLow,
                                               @Param("screenId") Long screenId);

}
