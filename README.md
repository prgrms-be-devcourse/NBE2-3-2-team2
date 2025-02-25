<h1>🎬 프로젝트 소개</h1> 

- **사용자 중심의 영화 예매 경험 제공**
    - 좌석 현황, 상영관 정보, 예매에 필요한 핵심 정보를 직관적으로 제공
- **효율적인 영화 예매 플랫폼 구축**
    - 영화 상영 시간표, 잔여 좌석, 결제 시스템을 통합적으로 관리하는 플랫폼 개발

## ✨ 팀 소개

|                          BE                           |                            BE                          |               BE                |                             BE                             |                             BE                             |
|:---------------------------------------------------------:|:----------------------------------------------------------:|:--------------------------------:|:-----------------------------------------------------------:|:-----------------------------------------------------------:|
| ![](https://avatars.githubusercontent.com/u/134962465?v=4) | ![](https://avatars.githubusercontent.com/u/134962465?v=4) | ![](https://avatars.githubusercontent.com/u/134962465?v=4) | ![](https://avatars.githubusercontent.com/u/97494494?v=4) | ![](https://avatars.githubusercontent.com/u/134962465?v=4) |
|                          Team Leader                           |                            PM                           |               AWS Admin                |                             Developer & Clerk                             |                             BE                             |
|             [강시영](https://github.com/Si-rauis)              |         [이중호](https://github.com/bung-dev)          | [이시현](https://github.com/CryingPerson) |           [이용준](https://github.com/usingjun)           |           [이름](https://github.com/username)           |
| 사업자 관리<br>스터디룸 작성 및 관리 페이지<br>(검색 필터링)<br>Docker 컨테이너화<br>테스트 툴 연결 (K6, Grafana)<br>SSL 인증 | SpringSecurity / OAuth2 <br>Refresh Token<br>Rotation 레디스 저장<br>분산락을 통한 동시성 제어<br>결제 시스템 | 사용자 마이페이지<br>리뷰 작성 및 관리 페이지<br>실시간 알림 (SSE)<br>CI/CD<br>머신 러닝 추천 시스템<br>(Hybrid filtering) | 사업장 등록 및 상세 페이지<br>(카카오 지도 api)<br>레디스 pub/sub, 임시 저장소<br>활용한 실시간 1대 1 채팅<br>(WebSocket, STOMP) | 담당 업무
## 🛠 Tech Stack

### 🔨 Backend
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.4.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![Spring Batch](https://img.shields.io/badge/Spring_Batch-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

### 💾 Database
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-4479A1?style=for-the-badge&logo=spring&logoColor=white)

### 🎨 Template Engine
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)

### 📡 Communication
![RestTemplate](https://img.shields.io/badge/RestTemplate-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Ajax](https://img.shields.io/badge/Ajax-0769AD?style=for-the-badge&logo=jquery&logoColor=white)

### 📦 Packaging
![JAR](https://img.shields.io/badge/JAR-ED8B00?style=for-the-badge&logo=java&logoColor=white)

### 📚 Documentation
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### 🌐 External API
![KakaoPay](https://img.shields.io/badge/Kakao_Pay-FFCD00?style=for-the-badge&logo=kakao&logoColor=black)
![KMDB](https://img.shields.io/badge/영화진흥위원회-FF0000?style=for-the-badge&logo=film&logoColor=white)

🔧 Trouble Shooting
🎯 성능 최적화 담당자: 고진영
<table>
  <tr>
    <th width="30%">문제 상황</th>
    <th width="40%">해결 방안</th>
    <th width="30%">개선 효과</th>
  </tr>
  <tr>
    <td>관리자 페이지 영화 조회 성능 저하</td>
    <td>✅ 인덱스 설계 및 적용을 통한 조회 성능 개선</td>
    <td>조회 속도 향상</td>
  </tr>
  <tr>
    <td>JPQL 영화 상영시간 조회 성능 이슈</td>
    <td>✅ QueryDSL로 리팩토링하여 타입 안정성 확보 및 성능 개선</td>
    <td>유지보수성 향상 및 쿼리 최적화</td>
  </tr>
  <tr>
    <td>예매 프로세스 응답 지연</td>
    <td>✅ 비동기 로직 구현으로 처리 시간 단축</td>
    <td>사용자 경험 개선</td>
  </tr>
</table>
🔄 결제 시스템 담당자: 허정현
<table>
  <tr>
    <th width="30%">문제 상황</th>
    <th width="40%">해결 방안</th>
    <th width="30%">개선 효과</th>
  </tr>
  <tr>
    <td>미결제건 처리 자동화 필요</td>
    <td>✅ Spring Batch를 활용한 자동 처리 시스템 구축<br>✅ 일별/월별 대용량 결제 데이터 배치 처리</td>
    <td>운영 효율성 향상</td>
  </tr>
  <tr>
    <td>반복적인 HTTP 요청 코드</td>
    <td>✅ 공통 모듈화를 통한 보일러플레이트 코드 제거<br>✅ 템플릿 메서드 패턴 적용</td>
    <td>코드 재사용성 향상</td>
  </tr>
  <tr>
    <td>결제 실패 처리 이력 관리 필요</td>
    <td>✅ 결제 실패 로깅 시스템 구축<br>✅ 실패 원인 분석 및 추적 기능 구현</td>
    <td>장애 대응력 강화</td>
  </tr>
</table>
🔒 보안 & 인증 담당자: 조희지
<table>
  <tr>
    <th width="30%">문제 상황</th>
    <th width="40%">해결 방안</th>
    <th width="30%">개선 효과</th>
  </tr>
  <tr>
    <td>쿠키 보안 설정 개선 필요</td>
    <td>✅ HTTPS 전용 쿠키 설정<br>✅ 환경 설정 파일 분리<br>✅ 쿠키 관련 설정 서비스 로직으로 전환</td>
    <td>보안성 강화</td>
  </tr>
  <tr>
    <td>Spring Security 버전 호환성</td>
    <td>✅ Spring Security 6.1 내장 함수 적용<br>✅ FilterChain 설정 파일 분리</td>
    <td>보안 로직 최신화</td>
  </tr>
  <tr>
    <td>마이페이지 예약 조회 N+1 문제</td>
    <td>✅ 단일 쿼리로 조회 로직 개선<br>✅ 연관 데이터 일괄 조회</td>
    <td>조회 성능 최적화</td>
  </tr>
</table>
💾 상영 시스템 담당자: 박지현
<table>
  <tr>
    <th width="30%">문제 상황</th>
    <th width="40%">해결 방안</th>
    <th width="30%">개선 효과</th>
  </tr>
  <tr>
    <td>상영관 이름 불일치 문제</td>
    <td>✅ 상영관 삭제 시 참조 무결성 검증<br>✅ ID 매핑 로직 개선</td>
    <td>데이터 정합성 확보</td>
  </tr>
</table>
🔍 검색 시스템 담당자: 유수호
<table>
  <tr>
    <th width="30%">문제 상황</th>
    <th width="40%">해결 방안</th>
    <th width="30%">개선 효과</th>
  </tr>
  <tr>
    <td>JPA 인덱스 기능 한계</td>
    <td>✅ Native Query로 full-text index 구현<br>✅ 애플리케이션 시작 시 DDL 자동 설정</td>
    <td>전문 검색 기능 구현</td>
  </tr>
</table>

 .
├── README.md
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── letmovie
    │   │               ├── LetMovieApplication.java
    │   │               ├── domain
    │   │               │   ├── admin
    │   │               │   │   ├── controller
    │   │               │   │   ├── repository
    │   │               │   │   └── service
    │   │               │   ├── auth
    │   │               │   │   ├── controller
    │   │               │   │   ├── entity
    │   │               │   │   ├── repository
    │   │               │   │   ├── security
    │   │               │   │   ├── service
    │   │               │   │   └── util
    │   │               │   ├── board
    │   │               │   ├── member
    │   │               │   │   ├── controller
    │   │               │   │   ├── dto
    │   │               │   │   ├── entity
    │   │               │   │   ├── repository
    │   │               │   │   └── service
    │   │               │   ├── movie
    │   │               │   │   ├── config
    │   │               │   │   ├── controller
    │   │               │   │   ├── dto
    │   │               │   │   ├── entity
    │   │               │   │   ├── mock
    │   │               │   │   ├── repository
    │   │               │   │   └── service
    │   │               │   ├── payment
    │   │               │   │   ├── controller
    │   │               │   │   ├── dto
    │   │               │   │   ├── entity
    │   │               │   │   ├── provider
    │   │               │   │   ├── repository
    │   │               │   │   ├── scheduler
    │   │               │   │   ├── service
    │   │               │   │   └── util
    │   │               │   └── reservation
    │   │               │       ├── controller
    │   │               │       ├── dto
    │   │               │       ├── entity
    │   │               │       ├── facade
    │   │               │       ├── repository
    │   │               │       └── service
    │   │               └── global
    │   │                   ├── advice
    │   │                   ├── config
    │   │                   └── exception
    │   │                       ├── controller
    │   │                       └── exceptionClass
    │   │                           ├── auth
    │   │                           ├── payment
    │   │                           └── reservation
    │   └── resources
    │       ├── application.properties
    │       ├── db
    │       ├── static
    │       │   ├── css
    │       │   ├── images
    │       │   └── js
    │       └── templates
    └── test
        └── java
            └── com
                └── example
                    └── letmovie
                        ├── LetMovieApplicationTests.java
                        └── domain
                            └── reservation
                                ├── controller
                                ├── facade
                                └── service
