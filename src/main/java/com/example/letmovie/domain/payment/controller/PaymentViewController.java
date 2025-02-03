package com.example.letmovie.domain.payment.controller;

import com.example.letmovie.domain.auth.util.SecurityUtil;
import com.example.letmovie.domain.member.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentViewController {

    @GetMapping("/success")
    public String showPaymentSuccess(Model model) {
        Member member = SecurityUtil.getCurrentMember()
                .orElseThrow(() -> new IllegalArgumentException("멤버가 존재하지 않습니다."));

        model.addAttribute("member", member);
        model.addAttribute("isLoggedIn", true);  // 이 부분 추가
        return "payment-success";
    }

    @GetMapping("/ready")
    public String myPayment() {
        return "payment-ready";
    }

    @GetMapping("/paymentlist")
    public String myPaymentPage(Model model) {
        Member member = SecurityUtil.getCurrentMember()
                .orElseThrow(() -> new IllegalArgumentException("멤버가 존재하지 않습니다."));

        model.addAttribute("member", member);
        model.addAttribute("isLoggedIn", true);  // 이 부분 추가
        return "paymentlist";
    }
}
