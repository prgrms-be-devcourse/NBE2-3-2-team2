package com.example.letmovie.domain.movie.controller;

import com.example.letmovie.domain.movie.service.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class SearchController {

    private final MovieServiceImpl movieService;

    @GetMapping({"/api/search/suggestions", "/private/api/search/suggestions"})
    public List<Map<String, Object>> getSuggestions(@RequestParam String query) {
        return movieService.searchMoviesByName(query).stream()
                .map(movie -> {
                    Map<String, Object> suggestion = new HashMap<>();
                    suggestion.put("id", movie.getId());
                    suggestion.put("name", movie.getMovieName());
                    return suggestion;
                })
                .toList();
    }
}
