package com.example.letmovie.domain.movie.controller;

import com.example.letmovie.domain.movie.dto.ShowtimeDTO;
import com.example.letmovie.domain.movie.service.ShowtimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeServiceImpl showtimeService;

    @GetMapping({"/movie/showtime", "/private/movie/showtime"})
    public String getShowtime(Model model) {
        List<ShowtimeDTO> showtime = showtimeService.getAllShowtime();

        showtime.add(new ShowtimeDTO(null, "이처럼 사소한 것들", "1관", 40, 12, LocalDate.of(2024, 12, 25), LocalTime.of(12, 0)));
        showtime.add(new ShowtimeDTO(null, "이처럼 사소한 것들", "2관", 40, 10, LocalDate.of(2024, 12, 26), LocalTime.of(12, 0)));
        showtime.add(new ShowtimeDTO(null, "이처럼 사소한 것들", "3관", 40, 40, LocalDate.of(2024, 12, 27), LocalTime.of(12, 0)));
        showtime.add(new ShowtimeDTO(null, "이처럼 사소한 것들", "4관", 40, 0,  LocalDate.of(2024, 12, 28), LocalTime.of(12, 0)));

        model.addAttribute("showtime", showtime);

        return "showtime";
    }
}