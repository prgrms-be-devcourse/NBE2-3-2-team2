package com.example.letmovie.domain.movie.repository;

import com.example.letmovie.domain.movie.dto.ShowtimeDTO;
import com.example.letmovie.domain.movie.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowtimeJpaRepository extends JpaRepository<Showtime, Long> {

    @Query("SELECT st FROM Showtime st JOIN FETCH st.movie m JOIN FETCH st.screen s")
    List<ShowtimeDTO> findAllShowtime();
}
