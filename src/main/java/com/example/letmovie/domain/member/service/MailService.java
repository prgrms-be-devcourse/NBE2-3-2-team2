package com.example.letmovie.domain.member.service;

import com.example.letmovie.domain.member.entity.VerificationCode;
import com.example.letmovie.domain.member.repository.MemberRepository;
import com.example.letmovie.domain.member.repository.VerificationCodeRepository;
import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.payment.TooManyRequestsException;
import com.example.letmovie.global.exception.exceptionClass.payment.VerificationCodeException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Getter
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    private static final int CODE_EXPIRATION_MINUTES = 5;
    private static final int MAX_REQUESTS_PER_DAY = 5;

    private final String SENDER_EMAIL = "hjjo@gmail.com";
    private final MemberRepository memberRepository;

    public MimeMessage createMail(String recipientEmail) throws IllegalArgumentException, MessagingException, TooManyRequestsException {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(recipientEmail)
                .orElse(null);

        if (verificationCode == null || verificationCode.isExpired(CODE_EXPIRATION_MINUTES)) {
            verificationCode = new VerificationCode(recipientEmail, generateVerificationCode());
            verificationCodeRepository.save(verificationCode);
        } else if (!verificationCode.canRequestMore(MAX_REQUESTS_PER_DAY)) {
            throw new TooManyRequestsException("하루 최대 인증 코드 요청 횟수를 초과하였습니다.");
        } else {
            verificationCode.incrementRequestCount();
            verificationCodeRepository.save(verificationCode);
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(SENDER_EMAIL);
        message.setRecipients(MimeMessage.RecipientType.TO, recipientEmail);
        message.setSubject("LetMovie 이메일 인증");

        String body = String.format(
                "<h3>요청하신 인증 번호입니다.</h3><br><h1>%d</h1><br><h3>감사합니다.</h3>",
                verificationCode.getCode()
        );
        message.setContent(body, "text/html; charset=UTF-8");
        return message;
    }

    public int sendMail(String recipientEmail) {
        try {
            // 이메일 중복 검사
            if (memberRepository.existsByEmail(recipientEmail)) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }

            MimeMessage message = createMail(recipientEmail);
            javaMailSender.send(message);
            return verificationCodeRepository.findByEmail(recipientEmail)
                    .map(VerificationCode::getCode)
                    .orElseThrow(() -> new VerificationCodeException(ErrorCodes.VERIFICATION_CODE_SAVE_FAILED));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (MessagingException e) {
            throw new VerificationCodeException(ErrorCodes.EMAIL_SEND_FAILED);
        } catch (TooManyRequestsException e) {
            throw new TooManyRequestsException(e.getMessage());
        }
    }

    private int generateVerificationCode() {
        return new Random().nextInt(900000) + 100000; // 6자리 숫자 코드
    }

    @Scheduled(cron = "0 0 * * * *")
    public void cleanUpExpiredCodes() {
        verificationCodeRepository.findAll().forEach(code -> {
            if (code.isExpired(10)) { // 10분 초과 시 코드 만료
                verificationCodeRepository.deleteByEmail(code.getEmail());
            }
        });
    }

}
