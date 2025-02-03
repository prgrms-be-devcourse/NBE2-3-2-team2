package com.example.letmovie.domain.admin.service;

import com.example.letmovie.domain.admin.repository.AdminScreenRepository;
import com.example.letmovie.domain.admin.repository.AdminSeatRepository;
import com.example.letmovie.domain.admin.repository.AdminShowtimeRepository;
import com.example.letmovie.domain.reservation.entity.Screen;
import com.example.letmovie.domain.reservation.entity.Seat;
import com.example.letmovie.domain.reservation.entity.SeatType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSeatServiceImpl {
    @Autowired
    private AdminScreenRepository adminScreenRepository;

    @Autowired
    private AdminSeatRepository adminSeatRepository;

    @Autowired
    private AdminShowtimeRepository adminShowtimeRepository;

    // 좌석
    public List<Screen> getAllScreens() {
        return adminScreenRepository.findAll();
    }

    // 좌석 추가 로직
    @Transactional
    public void addSeatsToScreen(Long screenId, int seatLow, int seatCol) {
        Screen screen = adminScreenRepository.findById(screenId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid screen ID"));
        for (int row = 1; row <= seatLow; row++) {
            for (int col = 1; col <= seatCol; col++) {
                if (adminSeatRepository.existsByScreenAndSeatLowAndSeatCol(screen, row, col)) {
                    continue;
                }
                Seat seat = Seat.builder()
                        .screen(screen)
                        .seatLow(row)
                        .seatCol(col)
                        .seatType(SeatType.REGULAR)
                        .price(10000)
                        .build();
                adminSeatRepository.save(seat);
            }
        }
    }


    // 특정 상영관의 좌석 가져오기
    public List<Seat> getSeatsByScreenId(Long screenId) {
        return adminSeatRepository.findByScreenId(screenId);
    }

    public Seat getSeatById(Long seatId) {
        return adminSeatRepository.findById(seatId).orElseThrow(() -> new IllegalArgumentException("Invalid seat ID"));
    }

    // 좌석 정보 수정
    public void updateSeat(Long seatId, SeatType seatType, int price) {
        Seat seat = adminSeatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seat ID"));
        seat.setSeatType(seatType);
        seat.setPrice(price);
        adminSeatRepository.save(seat);
    }

    @Transactional
    public void deleteAllSeatsByScreenId(Long screenId) {
        Screen screen = adminScreenRepository.findById(screenId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid screen ID"));

        boolean hasShowtime = adminShowtimeRepository.existsByScreenId(screenId);
        if (hasShowtime) {
            throw new IllegalStateException("해당 상영관에 상영 시간이 존재하여 좌석을 삭제할 수 없습니다.");
        }

        adminSeatRepository.deleteByScreen(screen);
    }
}
