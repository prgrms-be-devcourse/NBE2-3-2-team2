package com.example.letmovie.domain.admin.controller;

import com.example.letmovie.domain.admin.service.AdminShowtimeServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminShowtimeController {
    @Autowired
    private AdminShowtimeServiceImpl adminService;

    // /admin/showtime : 상영시간대
    @GetMapping("/showtime")
    public String showtime(Model model) {
        model.addAttribute("showtimes", adminService.getAllShowtimes());

        Map<Long, String> screenNamesMap = adminService.getAllScreenNamesById();
        model.addAttribute("screenNames", screenNamesMap);

        Map<Long,String> movieNamesMap = adminService.getAllMovieNamesById();
        model.addAttribute("movieNames", movieNamesMap);

        return "admin/showtime/admin_showtime";
    }

    // /admin/showtime/add : 상영시간대 추가 화면
    @GetMapping("/showtime/add")
    public String addShowtimeForm(Model model) {
        model.addAttribute("screens", adminService.getAllScreens());
        model.addAttribute("movies", adminService.findAllMovies());
        return "admin/showtime/admin_showtime_add";
    }

    // /admin/showtime/add : 상영시간대 추가 처리
    @PostMapping("/showtime/add")
    public String addShowtime(@RequestParam Long screenId,
                              @RequestParam Long movieId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate showtimeDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime showtimeTime,
                              RedirectAttributes redirectAttributes) {
        int totalSeats = adminService.countAvailableSeatsByScreenId(screenId);
        int remainingSeats = totalSeats;

        adminService.addShowtime(screenId, movieId, showtimeDate, showtimeTime, totalSeats, remainingSeats);
        redirectAttributes.addFlashAttribute("message", "상영 일정이 성공적으로 추가되었습니다.");

        return "redirect:/admin/showtime";
    }

    // /admin/showtime/delete/1 : 상영시간대 삭제 처리
    @GetMapping("/showtime/delete/{id}")
    @Transactional
    public String deleteShowtime(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteShowtime(id);
        redirectAttributes.addFlashAttribute("message", "상영 일정이 성공적으로 삭제되었습니다.");
        return "redirect:/admin/showtime";
    }
}
