# Backend Base Template

## 실행 방법

### local

기본 실행 시 local 프로필 적용

### dev

java -jar backend.jar --spring.profiles.active=dev

### prod

java -jar backend.jar --spring.profiles.active=prod

---

## 프로젝트 구조

* core: 공통 모듈 (auth, security, response, exception, logging)
* domain: 도메인별 비즈니스 로직

---

## API 규약

응답 구조:

{
"success": true,
"code": "SUCCESS",
"message": "요청 성공",
"data": {}
}

---

## 에러 처리

* BasicException 사용
* ErrorCode enum 기준

---

## 페이징/검색

POST /xxx/search
PageQueryRequest<T> 구조 사용

---

## 인증

* JWT Access + Refresh
* Authorization: Bearer {token}

---

## 규칙

* entity 직접 반환 금지
* request/response DTO 분리
* mapper xml 사용

### swagger-ui url
* http://localhost:8080/swagger-ui/index.html
