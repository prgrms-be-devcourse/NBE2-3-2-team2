<h1>🎬 프로젝트 소개</h1> 

- **사용자 중심의 영화 예매 경험 제공**
    - 좌석 현황, 상영관 정보, 예매에 필요한 핵심 정보를 직관적으로 제공
- **효율적인 영화 예매 플랫폼 구축**
    - 영화 상영 시간표, 잔여 좌석, 결제 시스템을 통합적으로 관리하는 플랫폼 개발

## ✨ 팀 소개

|                          BE                           |                            BE                          |               BE                |                             BE                             |                             BE                             |
|:---------------------------------------------------------:|:----------------------------------------------------------:|:--------------------------------:|:-----------------------------------------------------------:|:-----------------------------------------------------------:|
| ![](https://avatars.githubusercontent.com/u/134962465?v=4) | ![](https://avatars.githubusercontent.com/u/133757475?v=4) | ![](https://avatars.githubusercontent.com/u/188425240?v=4) | ![](https://avatars.githubusercontent.com/u/77951852?v=4) | ![](https://avatars.githubusercontent.com/u/134962465?v=4) |
|                          Team Leader                           |                            BE                           |               BE                |                             BE                             |                             BE                             |
|             박지현              |         고진영          | 허정현 |           조희지           |           유수호           |
| 관리자 기능<br> |  영화 예매 기능 | 결제 시스템 | 로그인 기능 및 인증 | 영화 기능 및 검색
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

### 📡 HTTP 
![RestTemplate](https://img.shields.io/badge/RestTemplate-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Ajax](https://img.shields.io/badge/Ajax-0769AD?style=for-the-badge&logo=jquery&logoColor=white)

### 📦 Packaging
![JAR](https://img.shields.io/badge/JAR-ED8B00?style=for-the-badge&logo=java&logoColor=white)

### 📚 Documentation
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

### 🌐 External API
![KakaoPay](https://img.shields.io/badge/Kakao_Pay-FFCD00?style=for-the-badge&logo=kakao&logoColor=black)
![KMDB](https://img.shields.io/badge/영화진흥위원회-FF0000?style=for-the-badge&logo=film&logoColor=white)

### 🛠️ Collaboration Tools
![Jira](https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)


# 🔧 Trouble Shooting
### 🎯 성능 최적화 담당자: 고진영
<table>
  <tr>
    <th width="30%">문제 상황</th>
    <th width="40%">해결 방안</th>
    <th width="30%">개선 효과</th>
  </tr>
  <tr>
    <td>좌석 중복 예매 및 예매 시스템 응답 속도 지연</td>
    <td>✅ 비관적 락에서 낙관적 락으로 변경하여 평균 응답 속도 30% 개선</td>
    <td>응답 성능 향상 및 사용자 경험 개선</td>
  </tr>
  <tr>
    <td>관리자 페이지 영화 조회 성능 저하</td>
    <td>✅ 인덱스 설계 및 적용을 통한 조회 성능 개선</td>
    <td>조회 속도 향상</td>
  </tr>
  <tr>
    <td>JPQL 영화 상영시간 조회 시 런타임 오류 방지</td>
    <td>✅ QueryDSL로 리팩토링하여 컴파일 시점 오류 사전 방지 및 동적 쿼리 대응</td>
    <td>타입 안정성 및 유지보수성 향상</td>
  </tr>
</table>

### 🔄 결제 시스템 담당자: 허정현
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

### 🔒 보안 & 인증 담당자: 조희지
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

### 💾 상영 시스템 담당자: 박지현
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

### 🔍 검색 시스템 담당자: 유수호
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

![image](https://github.com/user-attachments/assets/beae7f05-d121-4307-88d1-4e14f71e0c34)

![스크린샷 2025-02-25 오후 10 21 01](https://github.com/user-attachments/assets/9c1cec73-a6eb-43c6-a655-9e80d1f7507f)
