package com.example.letmovie.domain.movie.service;


import com.example.letmovie.domain.movie.dto.ReviewDTO;
import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Review;
import com.example.letmovie.domain.movie.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl {

    private final ReviewJpaRepository reviewJpaRepository;

    public List<ReviewDTO> getReviewsByMovieId(int movieId) {
        return reviewJpaRepository.findByMovieId(movieId).stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getNickname(),
                        review.getRating(),
                        review.getContent()
                ))
                .collect(Collectors.toList());
    }

    // 리뷰 추가
    public void addReview(Long movieId, String nickname, String password, int rating, String content) {
        Review review = new Review(new Movie(movieId, null, null, null, null, null, null, null, null, null, null, null, null, null, null), nickname, password, rating, content);
        reviewJpaRepository.save(review);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId, String password) {
        System.out.println("delete review service");
        Review review = reviewJpaRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        if (!review.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        reviewJpaRepository.delete(review);
    }
}
