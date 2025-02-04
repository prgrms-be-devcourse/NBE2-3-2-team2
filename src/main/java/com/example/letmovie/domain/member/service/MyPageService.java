package com.example.letmovie.domain.member.service;

import com.example.letmovie.domain.auth.util.SecurityUtil;
import com.example.letmovie.domain.member.dto.response.ReservationDetailsDTO;
import com.example.letmovie.domain.member.dto.response.SeatDTO;
import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.member.repository.MemberRepository;
import com.example.letmovie.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationRepository reservationRepository;

    @Autowired
    public MyPageService(PasswordEncoder passwordEncoder, MemberRepository memberRepository, ReservationRepository reservationRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        SecurityUtil.setMemberRepository(memberRepository);  // memberRepository를 static 방식으로 설정
        this.reservationRepository = reservationRepository;
    }

    // 현재 회원의 예약 목록을 가져옴
    public List<ReservationDetailsDTO> getReservationsForCurrentMember(Long memberId) {
        List<ReservationDetailsDTO> reservations = reservationRepository.findReservationsByMemberId(memberId);
        for (ReservationDetailsDTO reservation : reservations) {
            List<SeatDTO> seats = reservationRepository.findSeatsByReservationId(reservation.getReservationId()) ;
            reservation.setSeats(seats); // SeatDTO 리스트를 추가
        }
        return reservations;
    }

    // 비밀번호 변경 및 저장
    public boolean changePassword(String currentPassword, String newPassword) {
        Member member = SecurityUtil.getCurrentMember()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        member.updatePassword(encodedPassword);

        memberRepository.save(member);
        return true;
    }
}
