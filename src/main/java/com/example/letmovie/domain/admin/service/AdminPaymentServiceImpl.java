package com.example.letmovie.domain.admin.service;

import com.example.letmovie.domain.admin.repository.AdminMemberRepository;
import com.example.letmovie.domain.admin.repository.AdminPaymentHistoryRepository;
import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.payment.entity.PaymentHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPaymentServiceImpl {

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @Autowired
    private AdminPaymentHistoryRepository adminPaymentHistoryRepository;

    // 닉네임으로 회원 조회
    public List<Member> findMemberByName(String nickname) {
        return adminMemberRepository.findByNicknameContainingIgnoreCase(nickname);
    }

    // 특정 회원의 결제 내역 조회
    public List<PaymentHistory> findPaymentHistoryByMemberId(Long memberId) {
        String partnerUserId = memberId.toString();
        return adminPaymentHistoryRepository.findByPartnerUserId(partnerUserId);
    }
}
