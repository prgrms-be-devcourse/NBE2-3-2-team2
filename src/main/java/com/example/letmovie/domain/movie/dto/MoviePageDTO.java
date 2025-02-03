package com.example.letmovie.domain.movie.dto;

import com.example.letmovie.domain.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MoviePageDTO {
    private List<Movie> content;
    private int currentPage;
    private int totalPages;

    public MoviePageDTO() {}
}
