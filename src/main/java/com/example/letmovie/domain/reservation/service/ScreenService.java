package com.example.letmovie.domain.reservation.service;

import com.example.letmovie.domain.reservation.entity.Screen;
import com.example.letmovie.domain.reservation.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScreenService {

    private final ScreenRepository screenRepository;

    public Optional<Screen> findById(Long id) {
        return screenRepository.findById(id);
    }
}
