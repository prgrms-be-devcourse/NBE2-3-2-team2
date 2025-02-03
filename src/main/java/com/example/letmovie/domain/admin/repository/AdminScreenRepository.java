package com.example.letmovie.domain.admin.repository;

import com.example.letmovie.domain.reservation.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findAllByOrderByTheaterIdAscScreenNameAsc();
}
