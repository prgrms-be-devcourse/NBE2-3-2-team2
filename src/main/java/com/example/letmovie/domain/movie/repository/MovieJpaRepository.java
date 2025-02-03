package com.example.letmovie.domain.movie.repository;

import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieJpaRepository extends JpaRepository<Movie, Integer> {

    // Spring Data JPA 자체는 full-text 검색을 직접 지원 x
    // 따라서 native query를 사용
    @Query(value = """
    SELECT * 
    FROM movie 
    WHERE 
        MATCH(movie_name) AGAINST (CONCAT('+', :query, '*') IN BOOLEAN MODE)
    """, nativeQuery = true)
    List<Movie> searchMoviesByNameFullText(@Param("query") String query);

    // 검색 기능(포함될 시)
    List<Movie> findByMovieNameContainingIgnoreCase(String movieName);

    // 검색 기능(처음부터 비교)
    List<Movie> findByMovieNameStartingWithIgnoreCase(String movieName);

    // 카테고리 별로 출력을 위해 Status 에 따라 가져오기
    List<Movie> findByStatus(Status status);

    // 카테고리 별로 출력을 위해 Status 에 따라 가져오기 + 페이징 추가
    Page<Movie> findByStatus(Status status, Pageable pageable); // 페이징 지원
}
