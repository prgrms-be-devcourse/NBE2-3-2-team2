package com.example.letmovie.domain.admin.repository;

import com.example.letmovie.domain.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminMovieJpaRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select m from Movie m")
    List<Movie> findAllMovies();

    @Query(value = "select m from Movie m where m.movieName like :movieName%")
    List<Movie> findMovieByName(@Param("movieName") String movieName);

    @Query(value = "select m from Movie m")
    Page<Movie> findAllMovieswithPage(Pageable pageable);
}
