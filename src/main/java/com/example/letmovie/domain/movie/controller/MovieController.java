package com.example.letmovie.domain.movie.controller;

import com.example.letmovie.domain.movie.dto.MoviePageDTO;
import com.example.letmovie.domain.movie.dto.ReviewDTO;
import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Status;
import com.example.letmovie.domain.movie.service.MovieServiceImpl;
import com.example.letmovie.domain.movie.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImpl movieService;
    private final ReviewServiceImpl reviewService;

    // 전체 영화를 뿌리던 기존 방식에서 영화의 상태 별로 개수 제한을 두어 뿌리는 식으로 변경
    @GetMapping({"/", "/private"})
    public String homePage(Model model) {

        // 영화 데이터를 Status별로 개수를 제한하여 가져옴
        List<Movie> recommendMovies = movieService.getMoviesByStatusWithLimit(Status.RECOMMEND, 20);
        List<Movie> boxOfficeMovies = movieService.getMoviesByStatusWithLimit(Status.SHOW, 20);
        List<Movie> upcomingMovies = movieService.getMoviesByStatusWithLimit(Status.PREV, 20);

        model.addAttribute("recommendMovies", recommendMovies);
        model.addAttribute("boxOfficeMovies", boxOfficeMovies);
        model.addAttribute("upcomingMovies", upcomingMovies);

        return "home";
    }

    // 영화 상세 페이지
    @GetMapping({"/movie/{movieId}", "private/movie/{movieId}"})
    public String movieDetail(@PathVariable int movieId, Model model) {

        Movie movie = movieService.getMovieById(movieId);
        List<ReviewDTO> reviews = reviewService.getReviewsByMovieId(movieId); // 해당 영화의 리뷰 목록 가져오기
        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviews);

        return "movie_detail";
    }

    @GetMapping({"/movies", "private/movies"})
    public String moviesByCategory(
            @RequestParam(defaultValue = "ALL") String category,
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {

        try { // 예외 처리 추가
            if (query != null && !query.isBlank()) { // isBlank()로 빈 문자열 체크
                // 검색 로직
                List<Movie> movies = movieService.searchMoviesByName(query.strip()); // 공백 제거
                model.addAttribute("movies", movies);
                model.addAttribute("query", query);

            } else {
                // 카테고리 처리
                Status status = parseCategory(category); // 카테고리 파싱 분리
                MoviePageDTO moviePage = getMoviePage(status, page, size);

                // 모델 설정
                setupPaginationModel(model, moviePage, page);
            }
        } catch (IllegalArgumentException e) {
            log.error("잘못된 카테고리 입력: {}", category);
            model.addAttribute("error", "유효하지 않은 카테고리입니다.");
            return "error_page";
        }

        model.addAttribute("category", category.toUpperCase());

        return "total_movie";
    }

    // 카테고리 파싱 메서드
    private Status parseCategory(String category) {
        return switch (category.toUpperCase()) {
            case "RECOMMEND" -> Status.RECOMMEND;
            case "PREV" -> Status.PREV;
            default -> null;
        };
    }

    // 페이지 조회 로직
    private MoviePageDTO getMoviePage(Status status, int page, int size) {
        return (status != null)
                ? movieService.getMoviesByStatus(status, page, size)
                : movieService.getPagedMovies(page, size);
    }

    // 페이징 모델 설정
    private void setupPaginationModel(Model model, MoviePageDTO moviePage, int page) {
        int totalPages = moviePage.getTotalPages();
        int startPage = Math.max(1, (page / 10) * 10 + 1);
        int endPage = Math.min(startPage + 9, totalPages);

        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("page", page);
        model.addAttribute("pageNumbers", IntStream.rangeClosed(startPage, endPage)
                .boxed().collect(Collectors.toList()));
    }

}
