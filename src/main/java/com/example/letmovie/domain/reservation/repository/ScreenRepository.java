package com.example.letmovie.domain.reservation.repository;

import com.example.letmovie.domain.reservation.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
}
