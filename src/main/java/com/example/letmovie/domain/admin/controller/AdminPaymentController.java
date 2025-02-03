package com.example.letmovie.domain.admin.controller;

import com.example.letmovie.domain.admin.service.AdminPaymentServiceImpl;
import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPaymentController {
    @Autowired
    private AdminPaymentServiceImpl adminService;

    // /admin/payment : 회원 결제 관리 메인페이지
    @GetMapping("/payment")
    public String payment() {
        return "admin/payment/admin_payment";
    }

    // /admin/payment/membersearch : 회원 닉네임 검색
    @GetMapping("/payment/membersearch")
    public String paymentMemberSearch(@RequestParam("inputnickname") String nickname, Model model) {
        try {
            List<Member> members = adminService.findMemberByName(nickname);
            if(members != null) {
                model.addAttribute("members", members);
            } else {
                model.addAttribute("error", "검색 결과가 없습니다. 검색어를 확인해주세요.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "오류가 발생했습니다. 다시 시도해주세요.");
        }
        return "admin/payment/admin_payment_membersearch";
    }

    // /admin/payment/1 : 회원 결제내역 조회
    @GetMapping("/payment/{memberId}")
    public String viewMemberPayments(@PathVariable("memberId") Long memberId, Model model) {
        List<PaymentHistory> paymentHistories = adminService.findPaymentHistoryByMemberId(memberId);
        model.addAttribute("paymentHistories", paymentHistories);
        return "admin/payment/admin_payment_memberpaymenthistory";
    }
}
