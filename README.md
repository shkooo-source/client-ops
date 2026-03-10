# ClientOps - API 인증 장애 자동 감지 및 모니터링 시스템

Spring Boot 기반 API 모니터링 시스템 - API 인증 실패 패턴을 감지하여 Incident를 자동 생성

ClientOps는 외부 API 서비스 운영 시 발생할 수 있는 **인증 실패 패턴을 자동으로 감지하고 Incident를 생성하는 API 운영 모니터링 시스템**입니다.

API 요청 로그를 분석하여 반복적인 인증 실패나 비정상 접근을 감지하고, 운영자가 빠르게 문제 상황을 파악할 수 있도록 설계되었습니다.

---

## 🔗 Live Demo

Backend API Server  
https://client-ops-backend.onrender.com

Swagger API Docs  
https://client-ops-backend.onrender.com/swagger-ui/index.html

> Swagger UI를 통해 전체 API endpoint를 테스트할 수 있습니다.

Admin Dashboard  
https://client-ops-dashboard.netlify.app/

GitHub Repository  
https://github.com/shkooo-source/client-ops

※ 루트 URL은 API 서버 상태 확인용 endpoint입니다.
Swagger UI에서 전체 API 문서를 확인할 수 있습니다.

---

## 🛠 Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- REST API

### Frontend
- React
- Vite

### Database
- PostgreSQL (Production)
- MySQL (Local)

### Tool
- Maven
- Postman
- VSCode

---

## ✨ 주요 기능

### 1️⃣ API Key 기반 인증

외부 고객사별 API Key를 이용하여 요청 인증을 처리합니다.

요청 헤더 예시

```
X-API-KEY: test-api-key-1234
```

유효하지 않은 API Key 요청 시

```
401 Unauthorized
```

응답을 반환하도록 구현했습니다.

---

### 2️⃣ API 요청 로그 저장

모든 API 요청 정보를 **api_request_logs 테이블에 저장**합니다.

저장되는 정보

- Endpoint
- HTTP Method
- Status Code
- API Key
- Request Time
- Client 정보

이를 통해 API 사용 이력을 추적할 수 있도록 설계했습니다.

---

### 3️⃣ 인증 실패 패턴 분석

특정 API Key에서 **5분 내 인증 실패 3회 이상** 발생할 경우 이상 접근으로 판단하여 Incident를 자동 생성합니다.

---

### 4️⃣ Incident 관리

Incident 상태를 다음과 같이 관리합니다.

```
OPEN
RESOLVED
```

운영자는 내부 관리 API를 통해

- 현재 발생한 Incident 조회
- 장애 해결 처리

를 수행할 수 있습니다.

---

### 5️⃣ Admin Dashboard

React 기반 관리자 Dashboard를 구현했습니다.

Dashboard에서 확인 가능한 정보

- 전체 API 요청 수
- 에러 발생 수
- 평균 에러율
- 최근 API 요청 로그

이를 통해 API 운영 상태를 한눈에 확인할 수 있도록 구성했습니다.

---

## ■ System Architecture

```
Client
   ↓
Spring Boot API Server
   ↓
API Request Log 저장 (Database)
   ↓
인증 실패 패턴 분석
   ↓
Incident 생성
   ↓
React Admin Dashboard 표시
```

---

## ■ 주요 API

외부 API

```
GET /api/v1/orders
```

외부 고객사의 주문 정보를 조회하는 API

---

운영 관리 API

```
GET /internal/incidents
POST /internal/incidents/{id}/resolve
```

Incident 상태를 RESOLVED로 변경

---

## ■ Database 구조

주요 테이블

```
clients
api_request_logs
incidents
```

ERD 구조

```
Client
   ↓
API Request Logs
   ↓
Incident
```

API 요청 로그를 기반으로 인증 실패 패턴을 분석하여 Incident를 생성하도록 설계했습니다.

---

## ■ 배포 환경

Backend

```
Render
```

Frontend

```
Netlify
```

Database

```
PostgreSQL (Render)
```

---

## ■ 향후 개선 사항

향후 다음 기능을 추가할 계획입니다.

- OPEN 상태 API Key 자동 차단 기능
- API Gateway 기반 접근 제어
- 인증 실패 알림 시스템 (Slack / Email)
- API 요청 통계 분석 기능 확장

---

## ■ 프로젝트 문서

프로젝트 상세 문서는 **Notion / PDF 포트폴리오 문서**에 정리되어 있습니다.
