package com.example.letmovie.domain.movie.repository;

import com.example.letmovie.domain.movie.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovieId(int movieId);
}
