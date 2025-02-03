package com.example.letmovie.domain.admin.controller;

import com.example.letmovie.domain.admin.service.AdminScreenServiceImpl;
import com.example.letmovie.domain.movie.entity.Theater;
import com.example.letmovie.domain.reservation.dto.request.ScreenDTO;
import com.example.letmovie.domain.reservation.entity.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminScreenController {
    @Autowired
    private AdminScreenServiceImpl adminService;

    // /admin/screen : 상영관
    @GetMapping("/screen")
    public String screenManagement(Model model) {
        List<Screen> screens = adminService.findAllScreensSorted();
        List<Theater> theaters = adminService.findAllTheaters();

        model.addAttribute("screens", screens);
        model.addAttribute("theaters", theaters);
        return "admin/screen/admin_screen";
    }

    // /admin/screen/add : 상영관 추가 처리
    @PostMapping("/screen/add")
    public String addScreen(@ModelAttribute ScreenDTO screenDTO, RedirectAttributes redirectAttributes) {
        try {
            adminService.addScreen(screenDTO);
            redirectAttributes.addFlashAttribute("success", "상영관이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "상영관 추가 중 문제가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/admin/screen";
    }

    // /admin/screen/modify?id=1 : 상영관 수정 화면
    @GetMapping("/screen/modify")
    public String modifyScreen(@RequestParam("id") Long screenId, Model model) {
        Screen screen = adminService.findScreenById(screenId);
        if (screen != null) {
            ScreenDTO screenDTO = new ScreenDTO(screen.getId(), screen.getScreenName(), screen.getTheater().getId());
            model.addAttribute("screen", screenDTO);
        } else {
            model.addAttribute("error", "상영관을 찾을 수 없습니다.");
        }
        return "admin/screen/admin_screen_modify";
    }

    // /admin/screen/modify : 상영관 수정 처리
    @PostMapping("/screen/modify")
    public String modifyScreenSubmit(@ModelAttribute ScreenDTO screenDTO, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Received Screen ID: " + screenDTO.getId());
            adminService.updateScreen(screenDTO);
            redirectAttributes.addFlashAttribute("success", "상영관 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "상영관 수정 중 문제가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/admin/screen";
    }

    // /admin/screen/modify : 상영관 삭제 처리
    @PostMapping("/screen/delete")
    public String deleteScreen(@RequestParam("id") Long screenId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteScreen(screenId);
            redirectAttributes.addFlashAttribute("success", "상영관이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "상영관 삭제 중 문제가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/admin/screen";
    }
}
