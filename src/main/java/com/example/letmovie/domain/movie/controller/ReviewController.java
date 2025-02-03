package com.example.letmovie.domain.movie.controller;

import com.example.letmovie.domain.movie.dto.ReviewDTO;
import com.example.letmovie.domain.movie.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping("/add")
    public String addReview(@RequestParam Long movieId,
                            @RequestParam String nickname,
                            @RequestParam String password,
                            @RequestParam int rating,
                            @RequestParam String content) {
        reviewService.addReview(movieId, nickname, password, rating, content);
        System.out.println("add review controller");
        return "redirect:/private/movie/" + movieId;
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam Long reviewId,
                               @RequestParam String password,
                               @RequestParam int movieId,
                               RedirectAttributes redirectAttributes) {

        System.out.println("delete review controller");

        try {
            reviewService.deleteReview(reviewId, password);

            return "redirect:/private/movie/" + movieId;
        } catch (IllegalArgumentException e) {
            System.out.println("비밀번호 에러 발생");
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 오류");

            return "redirect:/private/movie/" + movieId;
        }
    }

}
