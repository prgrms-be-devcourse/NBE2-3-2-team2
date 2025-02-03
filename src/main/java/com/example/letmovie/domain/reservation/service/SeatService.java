package com.example.letmovie.domain.reservation.service;

import com.example.letmovie.domain.reservation.entity.Seat;
import com.example.letmovie.domain.reservation.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {

    private final SeatRepository seatRepository;

    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }
}
