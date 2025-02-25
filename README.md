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

### ERD 및 아키텍처

![LetMovie.png](attachment:bf403bdc-8670-4052-b72e-b3fcef8549a0:LetMovie.png)

![image.png](attachment:cd8e8e33-3bbf-4a7b-a677-7c671917d4ac:image.png)

### 프로젝트 구조

```
.
├── README.md
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── letmovie
    │   │               ├── LetMovieApplication.java
    │   │               ├── domain
    │   │               │   ├── admin
    │   │               │   │   ├── controller
    │   │               │   │   ├── repository
    │   │               │   │   └── service
    │   │               │   ├── auth
    │   │               │   │   ├── controller
    │   │               │   │   ├── entity
    │   │               │   │   ├── repository
    │   │               │   │   ├── security
    │   │               │   │   ├── service
    │   │               │   │   └── util
    │   │               │   ├── board
    │   │               │   ├── member
    │   │               │   │   ├── controller
    │   │               │   │   ├── dto
    │   │               │   │   ├── entity
    │   │               │   │   ├── repository
    │   │               │   │   └── service
    │   │               │   ├── movie
    │   │               │   │   ├── config
    │   │               │   │   ├── controller
    │   │               │   │   ├── dto
    │   │               │   │   ├── entity
    │   │               │   │   ├── mock
    │   │               │   │   ├── repository
    │   │               │   │   └── service
    │   │               │   ├── payment
    │   │               │   │   ├── controller
    │   │               │   │   ├── dto
    │   │               │   │   ├── entity
    │   │               │   │   ├── provider
    │   │               │   │   ├── repository
    │   │               │   │   ├── scheduler
    │   │               │   │   ├── service
    │   │               │   │   └── util
    │   │               │   └── reservation
    │   │               │       ├── controller
    │   │               │       ├── dto
    │   │               │       ├── entity
    │   │               │       ├── facade
    │   │               │       ├── repository
    │   │               │       └── service
    │   │               └── global
    │   │                   ├── advice
    │   │                   ├── config
    │   │                   └── exception
    │   │                       ├── controller
    │   │                       └── exceptionClass
    │   │                           ├── auth
    │   │                           ├── payment
    │   │                           └── reservation
    │   └── resources
    │       ├── application.properties
    │       ├── db
    │       ├── static
    │       │   ├── css
    │       │   ├── images
    │       │   └── js
    │       └── templates
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
```

### 공통 협업 방식 관리

- 깃 협업 방식
    
    ![image.png](attachment:133b9acd-01d1-4659-a1a9-d04baedaffab:image.png)
    
    ![image.png](attachment:8aca275c-9466-4cef-b841-d0a8a33e3f07:image.png)
    
    ![image.png](attachment:99820210-8256-410b-8758-5b9415367488:image.png)
    
- 깃 협업 방식
    
    ![image.png](attachment:133b9acd-01d1-4659-a1a9-d04baedaffab:image.png)
    
    ![image.png](attachment:8aca275c-9466-4cef-b841-d0a8a33e3f07:image.png)
    
    ![image.png](attachment:99820210-8256-410b-8758-5b9415367488:image.png)
    

- **Jira를 통해 개발 일정을 관리하고, Issue에 대해 다루었습니다.**
    
    ![image.png](attachment:1d073189-13b9-4447-b939-1f016feddc23:image.png)
    
    ![image.png](attachment:c5fc18b3-cdc1-40d4-960b-4c1b7b522a4c:image.png)
    
    ![image.png](attachment:48c1e35a-49b3-4990-9d23-b15a6320d2a4:image.png)
    
- **Slack을 통해 실시간으로 의사소통을 진행하였습니다.**
    
    ![image.png](attachment:f4b68f1c-5a6d-4990-91e3-8aa3aecdae7c:image.png)
    
- **zep을 통해 소통과 회의**
    
    ![image.png](attachment:8e00af97-a826-4ba3-be01-dc3fd301020b:image.png)
    

### 와이어 프레임 / UI

**와이어프레임**

![image.png](attachment:785351c6-9982-453f-b8b1-3c5ac357a4c3:image.png)

**화면 설계 (영상 추가!!!)**

https://ndb796.tistory.com/557

- admin
    
    영화 메인, 영화추가
    
    ![영화메인_영화추가 (1).gif](attachment:ea238ada-7d82-4087-863b-66b78cff4b0c:영화메인_영화추가_(1).gif)
    
    영화수정
    
    ![영화수정.gif](attachment:d834e3ca-1af3-4323-948a-4de225b805fa:영화수정.gif)
    
    영화 삭제
    
    ![영화삭제.gif](attachment:c0e4c767-e1cf-4c3c-b575-baf2b5ff46f1:영화삭제.gif)
    
    영화관
    
    ![영화관.gif](attachment:9bc9a399-23cb-49bb-9200-0f59e390a92a:영화관.gif)
    
    상영관
    
    ![상영관.gif](attachment:e09ac90e-2f13-4eb4-a22a-d59c42b0f452:상영관.gif)
    
    좌석
    
    ![좌석관리.gif](attachment:6f13400e-0b36-421c-bea3-4bebc14bbd75:좌석관리.gif)
    
    상영시간대
    
    ![상영시간대.gif](attachment:3d9d31ac-0f07-41f5-a7fa-f47c414fa566:상영시간대.gif)
    
    회원관리
    
    ![회원관리.gif](attachment:6d0a16dd-6978-451a-aec9-f25db32fd476:회원관리.gif)
    
    회원결제관리
    
    ![회원결제내역.gif](attachment:d8700a5a-18b0-43a9-83e4-9980a8e9c097:회원결제내역.gif)
    
- movie
    
    
    홈페이지
    
    ![홈페이지.gif](attachment:888489cc-4fb7-482e-a91a-683d8401b0e2:홈페이지.gif)
    
    영화 상세 페이지
    
    ![영화 상세페이지.gif](attachment:17c95a8c-2363-425d-99b1-66b1b21f10b6:영화_상세페이지.gif)
    
    영화 카테고리
    
    ![영화 카테고리.gif](attachment:a3ab699d-dc1e-470d-9136-e4592ed2d615:영화_카테고리.gif)
    
    영화 검색
    
    ![영화 검색.gif](attachment:b4c68ca5-ceac-40aa-abc6-c97458d86a16:영화_검색.gif)
    
- member
    
    **회원 가입 및 로그인**
    
    ![로그인 및 회원가입.gif](attachment:974ea87b-4c9b-4ce4-bfaa-a590b8261b3d:로그인_및_회원가입.gif)
    
    **마이페이지 (비밀번호 변경, 예매 내역 조회)**
    
    ![마이페이지.gif](attachment:aa90b036-404f-4737-a7bd-811077a95a07:마이페이지.gif)
    
- Reservation
    
    ![image.png](attachment:c6d2a640-1051-4a49-b217-4c6cb7012c55:image.png)
    
    ![image.png](attachment:353f3022-dc2a-4f5e-9c6e-09bac40fb10a:image.png)
    
- Payment
    
    ![스크린샷 2025-02-24 오후 8.04.52.jpg](attachment:e9a146b8-9df0-468f-ad69-e50d2429b84f:스크린샷_2025-02-24_오후_8.04.52.jpg)
    
    ![스크린샷 2025-02-24 오후 8.06.41.jpg](attachment:be50aa30-1706-4ca2-b046-ba30115ba25b:스크린샷_2025-02-24_오후_8.06.41.jpg)
    

### **URL 구조 / API 명세**

### **요구사항 명세와 기능 명세 (바뀐 부분, 추가 부분 각자 기입)**

| 기능 | 요구사항 명 | 요구 사항 내용 | 중요도 | 비고 |
| --- | --- | --- | --- | --- |
| 사용자 | 회원 가입  | 이메일 주소와 비밀번호로 회원 가입 진행, 생년월일도 있어야 함 | 상 |  |
|  | 이메일 인증 | 회원 가입 시 유효한 이메일인지 확인 후 회원 가입을 성공시켜야 함 | 상 |  |
|  | 로그인  | 사용자가 등록한 이메일 주소와 비밀번호를 입력하여 로그인 진행, | 상 |  |
|  | 로그아웃  | 로그아웃 시 토큰 전부 삭제  | 상 |  |
|  | 비밀번호 변경 | 사용자의 비밀번호를 변경 할 수 있음 | 상 |  |
|  | 예매 내역 조회 | 사용자의 영화 예매 내역을 조회할 수 있음 | 상 |  |
|  | 사용자 등급 조회 | 사용자의 회원 등급을 조회 할 수 있어야 함 | 상 |  |
|  | 관리자 로그인 | 관리자를 위한 로그인 페이지가 따로 있어야 함 | 중 |  |
| 영화 | 영화 상세 정보 | 영화의 상세 정보(줄거리, 출연진, 관람등급) 내용 표시 | 상 | 메인화면 + 관리자 영화 추가 |
|  | 상영 영화 | 관리자가 설정해준 영화 목록을 가지고 나와 화면에 표시 | 상 |  |
|  | 개봉 예정 영화  | 관리자가 설정해준 개봉 예정인 영화 목록을 화면에 표시 | 하 |  |
| 예매  | 날짜 검색 관련  | 사용자가 선택한 날짜의 관, 시간, 영화 목록을 화면에 표시 | 상 |  |
|  | 예매 영화 검색 관련 | 사용자가 선택한 영화의 관, 시간을 화면에 표시 | 상 |  |
|  | 좌석 선택 관련 | 사용자가 선택한 좌석 수 만큼의 좌석을 선택할 수 있도록 함 | 상 |  |
| 결제 | 결제 | 카카오페이 API  | 하 |  |
|  | 회원 결제 내역  | 회원 결제 내역 보기 |  |  |
| 리뷰 게시판 | 리뷰 게시판 구현  | 사용자가 영화를 봤으면 인증 마크 부여, 누구나 영화 리뷰 작성이 가능하다.  | 상 |  |
|    | 리뷰 댓글 구현 | 사용자는 다른 사용자의 리뷰에 댓글을 남길 수 있다. | 상 |  |
|  | 리뷰 좋아요 | 리뷰에 좋아요를 클릭할 수 있음. 대댓글달기. | 상 |  |
|  | 리뷰 검색 | 게시판의 검색 조건에 영화명을 넣고 검색  | 상 |  |
| 관리자 | 회원 관리 | 등록된 회원 관리와 등급을 조정할 수 있다. |  |  |
|  | 영화 추가 | 관리자는 영화를 새로 추가 할 수 있다. |  |  |
|  | 영화 시간대 추가 |  러닝 타임에 영화관 관리 시간을 더해 정각 단위로 쪼개서 영화 상영 시간을 관리함 |  |  |
|  | 영화 삭제 | boolean으로 off  |  |  |

## Admin

| app | HTTP Method | 설명 | 로그인 권한 필요 | 관리자 권한 필요 |
| --- | --- | --- | --- | --- |
| **admin/movie** | **GET** | **상영 중인 영화 전체 조회** | ✅ | ✅ |
| **admin/movie/post** | **GET** | **영화 추가 첫 화면(영화 검색)** | ✅ | ✅ |
| **admin/movie/post/search** | **GET** | **영화 검색 결과 및 영화 등록 폼** | ✅ | ✅ |
| **admin/movie/post/add** | **POST** | **영화 추가 처리** | ✅ | ✅ |
|  |  |  |  |  |
| **admin/movie/modify** | **GET** | **영화 정보 수정 첫 화면(영화ID검색)** | ✅ | ✅ |
| **admin/movie/modify/search** | **GET** | **영화 검색 후 정보 수정 폼** | ✅ | ✅ |
| **admin/movie/modify/ok** | **POST** | **영화 정보 수정 처리** | ✅ | ✅ |
|  |  |  |  |  |
| **admin/movie/delete** | **GET** | **영화 삭제 첫 화면(영화ID검색)** | ✅ | ✅ |
| **admin/movie/delete/search** | **GET** | **영화 검색 후 삭제 확인 페이지** | ✅ | ✅ |
| **admin/movie/delete/ok** | **POST** | **영화 삭제 처리** | ✅ | ✅ |

| app | HTTP Method | 설명 | 로그인 권한 필요 | 관리자 권한 필요 |
| --- | --- | --- | --- | --- |
| **admin/theater** | **GET** | **등록된 영화관 전체조회** | ✅ | ✅ |
| **admin/theater/post** | **GET** | **영화관 등록 폼** | ✅ | ✅ |
| **admin/theater/post** | **POST** | **영화관 등록 처리** | ✅ | ✅ |
|  |  |  |  |  |
| **admin/theater/modify/{id}** | **GET** | **영화관 수정 폼** | ✅ | ✅ |
| **admin/theater/modify** | **POST** | **영화관 수정 처리** | ✅ | ✅ |
| **admin/theater/delete/{id}** | **POST** | **영화관 삭제 처리** | ✅ | ✅ |
|  |  |  |  |  |
| **admin/screen** | **GET** | **상영관 첫화면(상영관 목록, 상영관 추가 폼)** | ✅ | ✅ |
| **admin/screen/add** | **POST** | **상영관 추가 처리** | ✅ | ✅ |
| **admin/screen/modify** | **GET** | **상영관 수정 폼** | ✅ | ✅ |
| **admin/screen/modify** | **POST** | **상영관 수정 처리** | ✅ | ✅ |
| **admin/screen/delete** | **POST** | **상영관 삭제 처리** | ✅ | ✅ |

| app | HTTP Method | 설명 | 로그인 권한 필요 | 관리자 권한 필요 |
| --- | --- | --- | --- | --- |
| **admin/seat** | **GET** | **좌석 첫화면 (좌석처리 상영관 목록)** | ✅ | ✅ |
| **admin/seat/{screenId}** | **GET** | **좌석 추가 폼** | ✅ | ✅ |
| **admin/seat/{screenId}/add** | **POST** | **좌석 추가 처리** | ✅ | ✅ |
|  |  |  |  |  |
| **admin/seat/edit/{screenId}** | **GET** | **좌석 수정 메인 페이지(좌석 목록)** | ✅ | ✅ |
| **admin/seat/edit/seat/{seatId}** | **GET** | **좌석 수정 폼** | ✅ | ✅ |
| **admin/seat/edit/seat/{seatId}** | **POST** | **좌석 수정 처리** | ✅ | ✅ |
| **admin/seat/delete/all/{screenId}** | **POST** | **좌석 일괄 삭제 처리** | ✅ | ✅ |
|  |  |  |  |  |
| **admin/showtime** | **GET** | **상영시간대 첫화면(목록)** | ✅ | ✅ |
| **admin/showtime/add** | **GET** | **상영시간대 추가 폼** | ✅ | ✅ |
| **admin/showtime/add** | **POST** | **상영시간대 추가 처리** | ✅ | ✅ |
| **admin/showtime/delete/{id}** | **POST** | **상영시간대 삭제 처리** | ✅ | ✅ |

| app | HTTP Method | 설명 | 로그인 권한 필요 | 관리자 권한 필요 |
| --- | --- | --- | --- | --- |
| **admin/member** | **GET** | **회원 정보 첫 화면(닉네임,ID검색)** | ✅ | ✅ |
| **admin/member/search** | **GET** | **회원 정보 닉네임으로 검색 후** | ✅ | ✅ |
| **admin/member/modify** | **GET** | **회원 정보 ID로 검색 후 수정 폼** | ✅ | ✅ |
| **admin/member/modify/ok** | **POST** | **회원 정보수정 처리** | ✅ | ✅ |
|  |  |  |  |  |
| **admin/payment** | **GET** | **회원 결제 관리 첫화면(닉네임 검색)** | ✅ | ✅ |
| **admin/payment/membersearch** | **GET** | **회원 닉네임 검색 후 화면** | ✅ | ✅ |
| **admin/payment/{memberId}** | **GET** | **회원 별 결제내역 조회** | ✅ | ✅ |

## Payment

| **app** | **HTTP Method** | **설명** | **로그인 권한 필요** | **관리자 권한 필요** |
| --- | --- | --- | --- | --- |
| **api/v1/payment/ready** | **POST** | **결제 준비 API** | **✅** |  |
| **api/v1/payment/success** | **GET**  | **결제 성공 API** | **✅** |  |
| **api/v1/payment/cancel** | **POST** | **결제 취소 API** | **✅** |  |
| **api/v1/payment/my-payments** | **GET** | **사용자 결제 조회API** | **✅** |  |

## PaymentHistory

| **app** | **HTTP Method** | **설명** | **로그인 권한 필요** | **관리자 권한 필요** |
| --- | --- | --- | --- | --- |
| **api/v1/paymentHistory/{payment_histories}** | **GET** | **관리자 모든 결제 정보 조회 API** | **✅** | **✅** |

## Movie

| app | HTTP Method | 설명 | 로그인 권한 필요 | 관리자 권한 필요 |
| --- | --- | --- | --- | --- |
| / | GET | 홈 화면 | x | x |
| /movie/{movie_id} | GET | 영화 상세 화면, 영화 한줄 리뷰 | x | x |
| /movie/reservation | GET | 예매 화면 | ✅ | x |
| /movie/showtime | GET | 영화 상영시간 | x | x |
| /mypage | GET | 사용자 화면 | ✅ | x |
| /signup | GET | 회원 가입 화면 | x | x |
| /login | GET | 로그인 화면 | x | x |
| /movies | GET | 전체 영화 목록 | x | x |
| /review/add | POST | 리뷰 추가 | ✅ | x |
| /review/delete | POST | 리뷰 삭제 | ✅ | x |
| /api/search/suggestion | GET | 영화 검색 | x | x |
|  |  |  |  |  |

## Reservation

| app | HTTP Method | 설명 | 로그인 권한 필요 | 작성자 권한 필요 |
| --- | --- | --- | --- | --- |
| /reservation | GET | 극장,영화,상영관,시간 조회 | ✅ |  |
| /api/dates | POST | 날짜 선택 시 영화 리스트 조회 | ✅ |  |
| /api/movies | POST | 날짜,영화 선택 시 극장 리스트 조회 | ✅ |  |
| /api/showtimes | POST | 날짜,영화,극장 선택 시 상영시간표 리스트 조회 | ✅ |  |
| /seatSelection | GET | 좌석 선택 화면 | ✅ |  |
| /reserve-seats | POST | 좌석 선택 | ✅ |  |

## Member

| app | HTTP Method | 설명 | 로그인 권한 필요 | 작성자 권한 필요 |
| --- | --- | --- | --- | --- |
| /signup | POST | 회원 가입 |  |  |
| /send-email | POST | 이메일 인증 |  |  |
| /login | POST | 로그인 |  |  |
| /logout | POST | 로그아웃 | ✅ |  |
| /token/refresh | POST | 만료 토큰 재발급 |  |  |
| mypage/ | GET | 회원 정보 조회 | ✅ |  |
| mypage/changepassword | POST | 비밀번호 변경 | ✅ | ✅ |
| mypage/reservationdetails/ | GET | 예매 내역 조회 | ✅ |  |
| reservations/cancel | POST | 예매 취소 | ✅ |  |

### 발표자료

https://docs.google.com/presentation/d/1jxnKGMgKfIlTavegR3LSsGABCkaiwTG3zlEsBxNq8U4/edit#slide=id.g322ddcb5b5c_8_28
