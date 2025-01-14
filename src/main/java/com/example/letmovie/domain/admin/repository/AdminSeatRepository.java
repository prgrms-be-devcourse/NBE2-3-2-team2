package com.example.letmovie.domain.admin.repository;

import com.example.letmovie.domain.reservation.entity.Screen;
import com.example.letmovie.domain.reservation.entity.Seat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminSeatRepository extends JpaRepository<Seat, Long> {
    // 특정 상영관의 좌석 유무 확인
    boolean existsByScreenAndSeatLowAndSeatCol(Screen screen, int seatLow, int seatCol);

    // 특정 상영관의 모든 좌석 가져오기
    List<Seat> findByScreenId(Long screenId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Seat s WHERE s.screen = :screen")
    void deleteByScreen(@Param("screen") Screen screen);

    @Query("SELECT COUNT(s) FROM Seat s WHERE s.screen.id = :screenId AND s.isAble = true")
    int countAvailableSeatsByScreenId(@Param("screenId") Long screenId);
}
