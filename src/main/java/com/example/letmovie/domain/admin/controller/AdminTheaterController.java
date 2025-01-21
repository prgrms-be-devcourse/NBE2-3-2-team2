package com.example.letmovie.domain.admin.controller;

import com.example.letmovie.domain.admin.service.AdminServiceImpl;
import com.example.letmovie.domain.admin.service.AdminTheaterServiceImpl;
import com.example.letmovie.domain.movie.dto.TheaterDTO;
import com.example.letmovie.domain.movie.entity.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminTheaterController {
    @Autowired
    private AdminTheaterServiceImpl adminService;

    //영화관
    @GetMapping("/theater")
    public String theater(Model model) {
        model.addAttribute("theaters", adminService.findAllTheaters());
        return "admin_theater";
    }

    @GetMapping("/theater/post")
    public String theaterPost(Model model) {
        model.addAttribute("theater", Theater.builder().build());
        return "admin_theater_post";
    }

    @PostMapping("/theater/post")
    public String addTheater(@ModelAttribute Theater theater, RedirectAttributes redirectAttributes) {
        try {
            adminService.addTheater(theater);
            redirectAttributes.addFlashAttribute("success", "영화관이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "영화관 추가 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/theater";
    }

    @GetMapping("/theater/modify/{id}")
    public String modifyTheater(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Theater theater = adminService.findTheaterById(id);
            model.addAttribute("theater", theater);
            return "admin_theater_modify"; // 극장 수정 뷰 이름
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "해당 극장을 찾을 수 없습니다.");
            return "redirect:/admin/theater";
        }
    }

    @PostMapping("/theater/modify")
    public String modifyTheater(@ModelAttribute TheaterDTO theaterDto, RedirectAttributes redirectAttributes) {
        //System.out.println("Received Theater: " + theater.getId() + ", " + theater.getTheaterName());
        try {
            adminService.updateTheater(theaterDto);
            redirectAttributes.addFlashAttribute("success", "극장이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "극장 수정 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/theater";
    }

    @PostMapping("/theater/delete/{id}")
    public String deleteTheater(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteTheaterById(id);
            redirectAttributes.addFlashAttribute("success", "극장이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "극장 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/theater";
    }
}
