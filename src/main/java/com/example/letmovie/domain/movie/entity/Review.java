package com.example.letmovie.domain.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 100)
    private String content;

    public Review(Movie movie, String nickname, String password, int rating, String content) {
        this.movie = movie;
        this.nickname = nickname;
        this.password = password;
        this.rating = rating;
        this.content = content;
    }
}
