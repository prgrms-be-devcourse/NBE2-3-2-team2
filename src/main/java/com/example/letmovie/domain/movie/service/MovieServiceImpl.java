package com.example.letmovie.domain.movie.service;

import com.example.letmovie.domain.movie.dto.MoviePageDTO;
import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Status;
import com.example.letmovie.domain.movie.repository.MovieJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MovieServiceImpl {

    private final MovieJpaRepository movieJpaRepository;

    public MovieServiceImpl(MovieJpaRepository movieJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
    }

    @Cacheable(value = "all_movies", key = "'allMovies' + '-' + #root.methodName")
    public List<Movie> getAllMovies() {
        return movieJpaRepository.findAll();
    }

    // Status별 영화 가져오기 + 개수 제한
//    @Cacheable(value = "movies_by_status_limited", key = "#status.name() + '-' + #limit")
//    public List<Movie> getMoviesByStatusWithLimit(Status status, int limit) {
//        // 페이징 처리를 하는 이유는 효율성 때문
//        // findAll을 한 후에 20개 제한을 걸어버리면 리소스 낭비
//        Pageable pageable = PageRequest.of(0, limit);
//        return movieJpaRepository.findByStatus(status, pageable).getContent();
//    }



    @Cacheable(value = "movies_by_status_limited", key = "{#status + '-' + #limit}")
    public List<Movie> getMoviesByStatusWithLimit(Status status, int limit) {

        if (status == null) {
            return Collections.emptyList();
        }
        Pageable pageable = PageRequest.of(0, limit);
        return movieJpaRepository.findByStatus(status, pageable).getContent();
    }

    public Movie getMovieById(int movieId) {
        return movieJpaRepository.findById(movieId).orElse(null);
    }

    /**
     * hashCode()를 사용한 이유는 캐시 키의 길이를 줄이고 효율적으로 관리하기 위함.
     * 긴 검색어 문자열을 그대로 키로 사용하면 메모리 사용량이 늘어나고, 특수 문자나 공백으로 인한 문제가 발생할 수 있음.
     * 해시 코드를 사용하면 고정된 길이의 숫자로 변환되어 이러한 문제를 방지할 수 있음.
     */

    // 검색 + 검색어 유효성 검사 추가
    @Cacheable(value = "movies", key = "#query.hashCode() + '-' + #root.methodName")
    public List<Movie> searchMoviesByName(String query) {

        if (query == null || query.isBlank()) {
            log.warn("빈 검색어 입력");
            return Collections.emptyList();
        }

        //List<Movie> movies = movieJpaRepository.findByMovieNameContainingIgnoreCase(query);

        List<Movie> movies = movieJpaRepository.searchMoviesByNameFullText(preprocessQuery(query));

        return movies;
    }

    // 쿼리 전처리
    private String preprocessQuery(String query) {
        return query.strip() // 앞뒤 공백 제거
                .replaceAll("\\s+", " ") // 중복 공백 제거
                .replaceAll("[^\\p{L}\\p{Nd}]", " "); // 특수문자 제거
    }

    // 캐시 무효화 로직 -> 아마 관리자 페이지에서 해주야 할듯(영화 업데이트나 삭제 등등)
    @CacheEvict(value = {"movies", "movies_by_status"}, allEntries = true)
    public void refreshCache() {
        log.info("영화 캐시 갱신");
    }

    // 카테고리
    public List<Movie> getMoviesByStatus(Status status) {
        return movieJpaRepository.findByStatus(status);
    }

    // 기본 페이징 처리
    @Cacheable(value = "movies_paged", key = "'all-' + #page + '-' + #size")
    public MoviePageDTO getPagedMovies(int page, int size) {
        Page<Movie> moviePage = movieJpaRepository.findAll(PageRequest.of(page - 1, size));
        return convertToDTO(moviePage);
    }

    // 상태별 페이징 처리
//    @Cacheable(value = "movies_by_status", key = "#status.name() + '-' + #page + '-' + #size")
    @Cacheable(value = "movies_by_status_limited", key = "{#status + '-' + #limit}")
    public MoviePageDTO getMoviesByStatus(Status status, int page, int size) {
        Page<Movie> moviePage = movieJpaRepository.findByStatus(status, PageRequest.of(page - 1, size));
        return convertToDTO(moviePage);
    }

    // Page -> DTO 변환 메서드
    private MoviePageDTO convertToDTO(Page<Movie> page) {
        return new MoviePageDTO(
                page.getContent(),
                page.getNumber() + 1,
                page.getTotalPages()
        );
    }
}
