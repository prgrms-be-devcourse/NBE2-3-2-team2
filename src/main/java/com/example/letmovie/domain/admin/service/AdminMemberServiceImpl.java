package com.example.letmovie.domain.admin.service;

import com.example.letmovie.domain.admin.repository.AdminMemberRepository;
import com.example.letmovie.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMemberServiceImpl {

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    // 닉네임으로 회원 조회
    public List<Member> findMemberByName(String nickname) {
        return adminMemberRepository.findByNicknameContainingIgnoreCase(nickname);
    }

    // ID로 회원 조회
    public Member findMemberById(Long memberId) {
        return adminMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. ID: " + memberId));
    }

    // 회원 수정
    public void updateMember(Member member) {
        Member existingMember = adminMemberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. ID: " + member.getId()));

        existingMember.updateNickname(member.getNickname());
        existingMember.changeGrade(member.getGrade());
        existingMember.changeStatus(member.getMemberStatus());

        adminMemberRepository.save(existingMember);
    }
}
