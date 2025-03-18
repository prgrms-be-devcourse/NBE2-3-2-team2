package com.example.letmovie.domain.admin.controller;

import com.example.letmovie.domain.admin.service.AdminSeatServiceImpl;
import com.example.letmovie.domain.reservation.entity.Seat;
import com.example.letmovie.domain.reservation.entity.SeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminSeatController {
    @Autowired
    private AdminSeatServiceImpl adminService;

    // /admin/seat : 좌석 메인페이지 (상영관을 보여줌)
    @GetMapping("/seat")
    public String seat(Model model) {
        model.addAttribute("screens", adminService.getAllScreens());
        return "admin/seat/admin_seat";
    }

    // /admin/seat/1 : 좌석추가폼
    @GetMapping("/seat/{screenId}")
    public String addSeatsForm(@PathVariable Long screenId, Model model) {
        model.addAttribute("screenId", screenId);
        return "admin/seat/admin_seat_post";
    }

    // /admin/seat/1/add : 좌석 추가 처리
    @PostMapping("/seat/{screenId}/add")
    public String addSeats(@PathVariable Long screenId, @RequestParam int seatLow, @RequestParam int seatCol) {
        adminService.addSeatsToScreen(screenId, seatLow, seatCol);
        return "redirect:/admin/seat";
    }

    // /admin/seat/edit/1 : 좌석수정 메인페이지 (해당하는 상영관이 가지는 좌석들 테이블을 모두 보여줌)
    @GetMapping("/seat/edit/{screenId}")
    public String editSeats(@PathVariable Long screenId, Model model) {
        model.addAttribute("seats", adminService.getSeatsByScreenId(screenId));
        model.addAttribute("screenId", screenId);
        return "admin/seat/admin_seat_list";
    }

    // /admin/seat/edit/seat/1 : 좌석 수정 페이지 접근
    @GetMapping("/seat/edit/seat/{seatId}")
    public String editSeatForm(@PathVariable Long seatId, Model model) {
        Seat seat = adminService.getSeatById(seatId);
        model.addAttribute("seat", seat);
        model.addAttribute("seatTypes", SeatType.values());
        return "admin/seat/admin_seat_edit";
    }

    // /admin/seat/edit/update : 좌석 수정 처리
    @PostMapping("/seat/edit/update")
    public String updateSeat(@RequestParam Long seatId, @RequestParam String seatType, @RequestParam int price) {
        adminService.updateSeat(seatId, SeatType.valueOf(seatType), price);
        return "redirect:/admin/seat";
    }

    // /admin/seat/delete/all/1 : 좌석 삭제 처리
    @PostMapping("/seat/delete/all/{screenId}")
    public String deleteAllSeats(@PathVariable Long screenId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteAllSeatsByScreenId(screenId);
            redirectAttributes.addFlashAttribute("message", "(Screen ID : " + screenId + ") 좌석이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "좌석 삭제 중 문제가 발생했습니다. : " + e.getMessage());
        }
        return "redirect:/admin/seat";
    }
}
