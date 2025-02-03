package com.example.letmovie.domain.member.controller;

import com.example.letmovie.domain.auth.util.SecurityUtil;
import com.example.letmovie.domain.member.dto.request.PasswordChangeRequestDTO;
import com.example.letmovie.domain.member.dto.response.ReservationDetailsDTO;
import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.member.service.MyPageService;
import com.example.letmovie.domain.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final ReservationService reservationService;

    @GetMapping("/mypage")
    @Operation(summary = "회원정보 뷰")
    public String myInfoPage(Model model) {
        Member member = SecurityUtil.getCurrentMember()
                .orElseThrow(() -> new IllegalArgumentException("멤버가 존재하지 않습니다."));

        model.addAttribute("member", member);

        return "mypage/mypage";
    }

    @GetMapping("/mypage/changepassword")
    @Operation(summary = "비밀번호 변경 뷰")
    public String changePassword() {

        return "/mypage/changepassword";
    }

    @PostMapping("/mypage/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequestDTO request) {

        // 비밀번호 확인 로직
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("새 비밀번호가 일치하지 않습니다.");
        }

       boolean isPasswordChanged = myPageService.changePassword(request.getCurrentPassword(), request.getNewPassword());

        if (!isPasswordChanged) {
            return ResponseEntity.badRequest().body("현재 비밀번호가 올바르지 않습니다.");
        }

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    @GetMapping("/mypage/reservationdetails")
    @Operation(summary = "예매내역 조회 뷰")
    public String reservationDetails(Model model) {
        try {
            Member member = SecurityUtil.getCurrentMember()
                    .orElseThrow(() -> new IllegalArgumentException("멤버가 존재하지 않습니다."));

            Long memberId = member.getId();
            List<ReservationDetailsDTO> reservations = myPageService.getReservationsWithSeats(memberId);
            model.addAttribute("reservations", reservations);

            return "mypage/reservation_details"; // 예매 내역 페이지 뷰
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
        }
    }

    @GetMapping("/mypage/paymentdetails")
    @Operation(summary = "결제내역 조회 뷰")
    public String paymentDetails() {

        return "mypage/payment_details";
    }

    @PostMapping("/reservations/cancel")
    public ResponseEntity<?> cancelReservation(@RequestParam("reservationId") Long reservationId) {
        try {
            reservationService.reservationCancel(reservationId); // 예매 취소 비즈니스 로직
            return ResponseEntity.ok("예매가 성공적으로 취소되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("예매 취소에 실패했습니다." + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }
}
