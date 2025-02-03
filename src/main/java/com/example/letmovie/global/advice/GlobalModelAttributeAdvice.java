package com.example.letmovie.global.advice;

import com.example.letmovie.domain.auth.util.SecurityUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    /**
     *  회원 비회원 구분 후 ui 할당
     */
    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        SecurityUtil.getCurrentMember().ifPresentOrElse(
                member -> {
                    model.addAttribute("isLoggedIn", true);
                    model.addAttribute("userNickname", member.getNickname());
                },
                () -> model.addAttribute("isLoggedIn", false)
        );
    }
}
