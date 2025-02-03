package com.example.letmovie.domain.movie.mock;

import com.example.letmovie.domain.member.entity.Authority;
import com.example.letmovie.domain.member.entity.Grade;
import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.member.entity.MemberStatus;
import com.example.letmovie.domain.member.repository.MemberRepository;
import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.movie.entity.Status;
import com.example.letmovie.domain.movie.entity.Theater;
import com.example.letmovie.domain.movie.repository.MovieJpaRepository;
import com.example.letmovie.domain.reservation.entity.Screen;
import com.example.letmovie.domain.reservation.entity.Seat;
import com.example.letmovie.domain.reservation.entity.SeatType;
import com.example.letmovie.domain.reservation.repository.ScreenRepository;
import com.example.letmovie.domain.reservation.repository.SeatRepository;
import com.example.letmovie.domain.reservation.repository.ShowtimeRepository;
import com.example.letmovie.domain.reservation.repository.TheaterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

@Component
@Profile("!test")
public class ShowtimeDataInitializer implements CommandLineRunner {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final MovieJpaRepository movieJpaRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    public ShowtimeDataInitializer(TheaterRepository theaterRepository, ScreenRepository screenRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository, MovieJpaRepository movieJpaRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.theaterRepository = theaterRepository;
        this.screenRepository = screenRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.movieJpaRepository = movieJpaRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Member member = Member.builder()
                .nickname("홍길동")
                .email("jinyoung@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .birthDate("19881213")
                .authority(Authority.USER) // 기본값이 USER라면 생략 가능
                .grade(Grade.GENERAL)          // 기본값이 GENERAL이라면 생략 가능
                .memberStatus(MemberStatus.AVAILABLE) // 기본값이 AVAILABLE이라면 생략 가능
                .build();

        Member admin = Member.builder()
                .nickname("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .birthDate("19901213")
                .authority(Authority.ADMIN)  // 관리자
                .grade(Grade.GENERAL)
                .memberStatus(MemberStatus.AVAILABLE)
                .build();

        Member test = Member.builder()
                .nickname("김영희")
                .email("user2@example.com")
                .password(passwordEncoder.encode("1212"))
                .birthDate("19901223")
                .authority(Authority.USER) // 기본값이 USER라면 생략 가능
                .grade(Grade.GENERAL)          // 기본값이 GENERAL이라면 생략 가능
                .memberStatus(MemberStatus.AVAILABLE) // 기본값이 AVAILABLE이라면 생략 가능
                .build();

        // 생성한 Member를 저장
        memberRepository.save(member);
        memberRepository.save(admin);
        memberRepository.save(test);

        Theater theater1 = theaterRepository.save(Theater.builder().id(null).theaterName("강남 메가박스").build());
        Theater theater2 = theaterRepository.save( Theater.builder().id(null).theaterName("신촌 CGV").build());
        Theater theater3 = theaterRepository.save(Theater.builder().id(null).theaterName("용산 아이맥스").build());

        // Screen 데이터 추가
        Screen screen1 = screenRepository.save(Screen.builder().theater(theater1).seats(new ArrayList<>()).screenName("1관").build());
        Screen screen2 = screenRepository.save(Screen.builder().theater(theater1).seats(new ArrayList<>()).screenName("2관").build());
        Screen screen3 = screenRepository.save(Screen.builder().theater(theater2).seats(new ArrayList<>()).screenName("3관").build());
        Screen screen4 = screenRepository.save(Screen.builder().theater(theater3).seats(new ArrayList<>()).screenName("아이맥스관").build());

        Movie movie1 = movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "팀 밀란츠", "12세이상관람가", "98",
                "20241211", "드라마", "아티스츠 이쿼티 빅 띵스 필름스",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "1985년 아일랜드의 소도시, 빌 펄롱은 석탄을 팔며 아내, 다섯 딸과 함께 소박하게 살아가고 있다. 크리스마스를 앞둔 어느 날, 빌 펄롱은 지역 수녀원에 석탄을 배달하러 가고 숨겨져 있던 어떤 진실을 마주하게 된다.", "100,000", "41,028"));
        Movie movie2 = movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "Kelly Marcel", "19세이상관람가", "109",
                "20241023", "액션,SF", "소니",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        Movie movie3 =movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "코미디", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
        Movie movie4 = movieJpaRepository.save(new Movie(null,
                "캐리온", "M0004", "감독 4", "전체관람가", "112",
                "20240810", "액션,스릴러", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie5 = movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240810", "액션", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie6 = movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240810", "모험,애니메이션", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie7 = movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "애니메이션", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie8 = movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "로맨스", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie9 = movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));

        // 상영 예정 영화
        movieJpaRepository.save(new Movie(null,
                "노스페라투", "M0001", "로버트 에거스", "19세이상관람가", "120분",
                "20250115", "공포,판타지", "메이든 보이지 픽쳐스",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/xeiARSpxGdVCw5KkCDgj31MO45o.jpg", "https://image.tmdb.org/t/p/original/uWOJbarUXfVf6B4o0368dh138eR.jpg", "남편의 부재 중 뱀파이어의 미스터리한 힘에 의해 게임에 빠져들면서 벌어지는 젊은 여성을 그린 공포 영화", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "리얼 페인", "M0002", "제시 아이젠버그", "15세이상관람가", "130",
                "20250115", "코미디,드라마", "토픽 스튜디오",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/yaOmgi2rdkusPK6oHj8XFru3kb8.jpg", "still2.jpg", "생김새부터 성격, 취향까지 모든 것이 다른 두 사촌 '데이비드'와 '벤지'. 돌아가신 할머니를 기리기 위해 오랜만에 재회한다. 한때는 형제처럼 친밀했지만 각자의 삶과 가족 등의 이유로 멀어졌던 둘의 관계는 할머니의 고향인 폴란드를 방문해 투어를 떠나게 되면서 새로운 국면을 맞이한다. 둘의 극과 극 성격은 투어에서도 균열을 만들고, 예상치 못한 일들이 생기면서, 미묘한 감정의 골 또한 더욱 커져만 가는데...", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "페라리", "M0003", "마이클 만", "15세이상관람가", "124",
                "20250118", "역사,드라마", "제작사 3",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/AwEIfVN091rsBzz8WSJCa58k5nr.jpg", "still1.jpg", "파산 위기에 놓인 엔초 페라리. 회사 존폐의 기로에서 사사건건 충돌하는 아내 라우라. 아들 피에로를 페라리 가로 인정하라고 압박하는 또다른 여인 리나. 평생 쌓아온 모든 것이 무너지기 직전인 1957년 여름, 이탈리아 전역 공도를 가로지르는 광기의 1,000마일 레이스 밀레 밀리아에서 엔초 페라리는 판도를 뒤집을 마지막 승부수를 던지는데...", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "언데드 다루는 법", "M0004", "Thea Hvistendahl", "15세이상관람가", "98",
                "20240122", "공포,미스터리", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/jvgoVL5h60anIw8qblv4SRCC7Gl.jpg", "still1.jpg", "손자이자 아들 엘리아스를 잃고 상실감에 괴로워하는 할아버지 말러와 엄마 안나, 아내 에바의 교통사고로 인한 사망 소식을 듣고 슬픔에 오열하는 남편 데이빗, 반려자 엘리자베트의 장례식을 마치고 텅 빈 집에 돌아온 노부인 토라. 원인불명의 정전이 오슬로 전역을 덮친 이후, 죽은 이들이 다시 깨어나 사랑하는 가족의 곁으로 돌아가기 시작한다, 무덤에 묻혔던 모습 그대로!", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "동화이지만 청불입니다", "M0004", "Lee Jong-suk", "19세이상관람가", "110",
                "20250108", "코미디,로맨스", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/gajOM92G0YOArS8nEvqxYQfhrEO.jpg", "still1.jpg", "동화 작가가 꿈이지만 현실은 불법 음란물 단속팀 새내기인 단비는 스타 작가를 찾던 성인 웹소설계 대부 황대표와 우연한 사고로 노예 계약을 맺게 되면서 하루아침에 19금 소설을 쓰게 된다. 생전 접한 적 없는 장르를 집필하는 데 난항을 겪던 단비는 음란물 단속을 하다 권태기에 빠진 선배 정석의 응원과, 친구들의 생생한 경험담에 힘입어 어느새 자신도 알지 못했던 성스러운 재능을 발견하게 되는데…", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "피스 바이 피스", "M0004", "Morgan Neville", "전체관람가", "112",
                "20250108", "애니메이션,음악", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/b2GOglK7cNsC8xXfxHG4k14otu4.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "애니멀 킹덤", "M0004", "Thomas Cailley", "15세이상관람가", "128",
                "20250122", "모험,SF", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/BlprxhtyKRzLYM0fS4QZRneewK.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "메모리", "M0004", "미셸 프랑코", "15세이상관람가", "112",
                "20250122", "드라마", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/yNyZKMvXtEiyr3pIyKYTPmSxaF2.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "0", "0"));

        // 추천 영화
        movieJpaRepository.save(new Movie(null,
                "듄:프로퍼시", "M0004", "감독 4", "15세이상관람가", "112",
                "20241210", "SF,액션", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/syQuoIyMmSrb7OmCub8y6RERhKf.jpg", "https://image.tmdb.org/t/p/original/lBoHzOgft2QfpjkVVvZCqeM4ttT.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20241210", "공포,스릴러", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "https://image.tmdb.org/t/p/original/ag66gJCiZ06q1GSJuQlhGLi3Udx.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "극장판 짱구는 못말려: 우리들의 공룡일기", "M0004", "Shinobu Sasaki", "전체관람가", "106",
                "20241218", "애니메이션", "신에이 동화",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/sxwOUajjGzkPYrrXy2sdMEfeZpE.jpg", "https://image.tmdb.org/t/p/original/vW7lwVHkRePHzayZfoKOyYBeZqO.jpg", "다이노스 아일랜드에 어서 오세요! 멸종된 공룡을 현대에 부활시킨 테마파크 다이노스 아일랜드 오픈! 떡잎마을은 물론, 전국이 공룡 열풍에 빠져든다! 그 무렵, 흰둥이는 어디선가 작은 공룡 나나를 발견한다. 나나는 짱구네 집의 새로운 가족이자 떡잎마을 방범대의 친구가 되어 아주 특별한 방학을 보내게 된다.", "300,000", "604,485"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20241223", "애니메이션", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "https://image.tmdb.org/t/p/original/ay8uvMrQNQcChIDMyfw60eAziQv.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20241223", "로맨스", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "https://image.tmdb.org/t/p/original/4cp40IyTpFfsT2IKpl0YlUkMBIR.jpg", "줄거리 4", "300,000", "12340"));

        // 페이징 확인 추가 데이터
        movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120",
                "20240510", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "19세이상관람가", "130",
                "20240620", "드라마", "제작사 2",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "SF", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
//        movieJpaRepository.save(new Movie(null,
//                "캐리온", "M0004", "감독 4", "전체관람가", "112",
//                "20240901", "코미디", "제작사 4",
//                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120",
                "20240510", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "19세이상관람가", "130",
                "20240620", "드라마", "제작사 2",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "SF", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
//        movieJpaRepository.save(new Movie(null,
//                "캐리온", "M0004", "감독 4", "전체관람가", "112",
//                "20240901", "코미디", "제작사 4",
//                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120",
                "20240510", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "19세이상관람가", "130",
                "20240620", "드라마", "제작사 2",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "SF", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
//        movieJpaRepository.save(new Movie(null,
//                "캐리온", "M0004", "감독 4", "전체관람가", "112",
//                "20240901", "코미디", "제작사 4",
//                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));

        Random random = new Random();

        // 영화 검색 성능 측청을 위한 데이터
        for (int i = 0; i < 100; i++) {
            String movieName = generateRandomMovieName(random);
            movieJpaRepository.save(new Movie(null,
                    movieName, "M0001", "감독 1", "15세이상관람가", "120",
                    "20240510", "액션", "제작사 1",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        }


// Showtime 데이터 추가
        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie1)
                .showtimeDate(LocalDate.of(2025, 1, 28))
                .showtimeTime(LocalTime.of(23, 30))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie2)
                .showtimeDate(LocalDate.of(2025, 1, 26))
                .showtimeTime(LocalTime.of(8, 30))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie3)
                .showtimeDate(LocalDate.of(2025, 1, 31))
                .showtimeTime(LocalTime.of(11, 0))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie4)
                .showtimeDate(LocalDate.of(2025, 1, 30))
                .showtimeTime(LocalTime.of(13, 0))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen2)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 1, 29))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(32)
                .remainingSeats(32)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen2)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 2, 5))
                .showtimeTime(LocalTime.of(14, 0))
                .totalSeats(32)
                .remainingSeats(32)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen3)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 2, 6))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(20)
                .remainingSeats(20)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen3)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 2, 7))
                .showtimeTime(LocalTime.of(14, 0))
                .totalSeats(20)
                .remainingSeats(20)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen4)
                .movie(movie9)
                .showtimeDate(LocalDate.of(2025, 2, 2))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(25)
                .remainingSeats(25)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen4)
                .movie(movie9)
                .showtimeDate(LocalDate.of(2024, 1, 25))
                .showtimeTime(LocalTime.of(14, 0))
                .totalSeats(25)
                .remainingSeats(25)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie5)
                .showtimeDate(LocalDate.of(2025, 1, 26))
                .showtimeTime(LocalTime.of(12, 30))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen4)
                .movie(movie5)
                .showtimeDate(LocalDate.of(2025, 1, 26))
                .showtimeTime(LocalTime.of(14, 50))
                .totalSeats(25)
                .remainingSeats(25)
                .build());


        // Seat 데이터 추가
        addSeatsForScreen(screen1, 10, 10);
        addSeatsForScreen(screen2, 8, 4);
        addSeatsForScreen(screen3, 5, 4);
        addSeatsForScreen(screen4, 5, 5);
    }

    private void addSeatsForScreen(Screen screen, int rows, int cols) {
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                boolean isAble = !(row == 6 && col == 2); // 특정 좌석 비활성화
                int price = (row > 9) ? 20000 : 10000; // VIP와 REGULAR 가격 분리
                SeatType seatType = (row > 9) ? SeatType.VIP : SeatType.REGULAR; // VIP와 REGULAR 가격 분리
                Seat seat = Seat.builder()
                        .screen(screen)
                        .seatType(seatType)
                        .seatLow(row)
                        .seatCol(col)
                        .price(price).build();
                seatRepository.save(seat);
            }
        }
    }

    // 랜덤 영화 제목
    private String generateRandomMovieName(Random random) {
        String[] adjectives = {"멋진", "사라진", "위대한", "숨겨진", "신비로운"};
        String[] nouns = {"모험", "전사", "왕국", "비밀", "시간", "사건"};
        return adjectives[random.nextInt(adjectives.length)] + " " + nouns[random.nextInt(nouns.length)];
    }
}
