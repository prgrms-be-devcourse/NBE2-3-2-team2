package com.example.letmovie.domain.member.controller;

import com.example.letmovie.domain.member.dto.request.SignupRequestDTO;
import com.example.letmovie.domain.member.service.MailService;
import com.example.letmovie.domain.member.service.MemberService;
import com.example.letmovie.global.exception.exceptionClass.payment.TooManyRequestsException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("/signup")
    @Operation(summary = "회원 가입 뷰")
    public String signupPage(Model model) {
        model.addAttribute("memberForm", new SignupRequestDTO());
        return "signup";
    }

    @PostMapping("/signup")
    @Operation(summary = "회원 가입 처리 API")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO request) {
        try {
            memberService.signup(request);
            // 성공
            return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 성공적으로 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            //  중복 닉네임 또는 이메일
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 발생 시
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 처리 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> sendMail(@RequestBody SignupRequestDTO request){
        try {
            int resultCode = mailService.sendMail(request.getEmail()); // 이메일 전송 후 인증 코드 생성

            Map<String, Object> response = new HashMap<>();
            response.put("authCode", resultCode);  // 인증 코드 전송

            return ResponseEntity.ok(response); // JSON 형식으로 반환
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage()); // 에러 메시지 전달

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (TooManyRequestsException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage()); // 에러 메시지 전달

            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorResponse);
        }
    }
}
