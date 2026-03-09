# 🚀 ClientOps: 실시간 API 모니터링 및 인시던트 관리 시스템

**ClientOps**는 외부 고객사에게 제공하는 API의 사용 내역을 실시간으로 추적하고, 반복적인 인증 실패 등 이상 징후 발생 시 자동으로 인시던트를 생성하여 관리자에게 알리는 통합 운영 플랫폼입니다.

---

## 🛠 Tech Stack
- **Backend**: Java 17, Spring Boot 3.2, Spring Data JPA
- **Frontend**: React 19, Vite
- **Database**: MySQL
- **Communication**: REST API (CORS 처리 완료)

---

## ✨ Key Features (주요 구현 사항)

### 1. API Key 기반 요청 검증 및 로깅
- 외부 고객사별 고유 `X-API-KEY`를 통한 유효성 검증 로직 구현
- 모든 API 요청의 엔드포인트, HTTP 메서드, 상태 코드를 실시간으로 기록하여 데이터 무결성 확보
- **관련 코드**: `ExternalApiController.java`, `ApiRequestLogService.java`

### 2. 실시간 장애 감지 (Incident Management)
- **장애 판단 로직**: 특정 API Key로 5분 이내 3회 이상의 인증 실패(401) 발생 시 이상 징후로 판단
- 중복 인시던트 방지 로직을 포함하여 'OPEN' 상태의 장애를 자동 생성
- **관련 코드**: `IncidentService.java`, `ApiRequestLogRepository.java`

### 3. 관리자 대시보드 (Frontend)
- **통계 시각화**: 전체 요청 수, 에러 발생 수, 실시간 에러율을 대시보드 카드로 제공
- **로그 조회**: 최신 API 요청 로그를 페이징(Pagination) 처리하여 효율적으로 조회 가능
- **관련 코드**: `App.jsx`, `AdminController.java`

---

## 🏗 System Architecture

1. **Client**: API Key를 포함하여 데이터 요청
2. **Spring Boot**: API Key 검증 및 로그 데이터 적재 (JPA/MySQL)
3. **Logic**: 인증 실패 누적 시 Incident 엔티티 생성
4. **React Dashboard**: 관리자용 API를 호출하여 시각화된 통계 및 로그 출력

---
