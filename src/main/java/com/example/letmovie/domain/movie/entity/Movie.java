package com.example.letmovie.domain.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(
        name = "movie",
        indexes = {
                @Index(name = "idx_movie_title", columnList = "movieName")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Table(
//        name = "movie",
//        indexes = {
//                @Index(name = "idx_movie_name", columnList = "movieName"), // movieName 컬럼에 대한 인덱스
//        }
//)
//@Table(
//        name = "movie",
//        indexes = {
//                @Index(name = "idx_movie_name_fulltext", columnList = "movie_name", unique = false) // Full-Text Index는 엔티티에서는 선언적으로만 가능
//        }
//)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String movieCode;

    @Column(nullable = false)
    private String directorName;

    @Column(nullable = false)
    private String rating;

    @Column(nullable = false)
    private String runtime;

    @Column(nullable = false)
    private String openDate;

    @Column(nullable = false)
    private String genreName;

    @Column(nullable = false)
    private String companys;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String posterImageUrl;

    @Column(nullable = false)
    private String stillImageUrl;

    @Column(nullable = false)
    private String plot;

    @Column(nullable = false)
    private String salesAcc;

    @Column(nullable = false)
    private String audiAcc;
}