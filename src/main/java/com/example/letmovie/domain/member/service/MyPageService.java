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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    // 예매 목록 조회
    @Transactional(readOnly = true)
    public List<ReservationDetailsDTO> getReservationsWithSeats(Long memberId) {
        List<ReservationDetailsDTO> reservations = reservationRepository.findReservationsWithSeats(memberId);
        Map<Long, ReservationDetailsDTO> reservationMap = new HashMap<>();

        for (ReservationDetailsDTO reservation : reservations) {
            if (!reservationMap.containsKey(reservation.getReservationId())) {
                reservationMap.put(reservation.getReservationId(), reservation);
            } else {
                reservationMap.get(reservation.getReservationId())
                        .addSeat(new SeatDTO( // SeatRow를 문자로 넣기 위해 새로 생성함
                                reservation.getSeats().get(0).getId(),
                                reservation.getSeats().get(0).getSeatLow(),
                                reservation.getSeats().get(0).getSeatCol()));
            }
        }
        return new ArrayList<>(reservationMap.values());
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
