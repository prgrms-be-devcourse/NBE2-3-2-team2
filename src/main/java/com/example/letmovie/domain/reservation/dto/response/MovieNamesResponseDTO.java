package com.example.letmovie.domain.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MovieNamesResponseDTO {

    private List<String> movieNames;

}
