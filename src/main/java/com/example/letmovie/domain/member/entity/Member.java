package com.example.letmovie.domain.member.entity;

import com.example.letmovie.domain.payment.entity.Payment;
import com.example.letmovie.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)   // Builder 를 위해 필요
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 8)
    private String birthDate; // 생년월일 (YYYYMMDD)

    @Column(nullable = false)
    private Authority authority = Authority.USER;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.GENERAL;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.AVAILABLE;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Payment> payments = new ArrayList<>();

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeGrade(Grade newGrade) {
        this.grade = newGrade;
    }

    public void changeStatus(MemberStatus newStatus) {
        this.memberStatus = newStatus;
    }
}
