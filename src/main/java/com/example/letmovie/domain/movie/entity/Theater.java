package com.example.letmovie.domain.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long id;

    @Column(nullable = false)
    private String theaterName;
}