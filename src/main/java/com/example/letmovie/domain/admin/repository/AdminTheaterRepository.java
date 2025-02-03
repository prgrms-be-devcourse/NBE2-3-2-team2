package com.example.letmovie.domain.admin.repository;

import com.example.letmovie.domain.movie.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTheaterRepository extends JpaRepository<Theater, Long> {
}
