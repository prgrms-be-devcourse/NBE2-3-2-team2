package com.example.letmovie.domain.admin.repository;

import com.example.letmovie.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByNicknameContainingIgnoreCase(String nickname);
}
