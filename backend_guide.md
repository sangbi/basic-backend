# Backend Base Template Guide

## 1. 실행 방법

### Local
기본 실행 시 local 프로필이 적용됩니다.

### Dev
java -jar backend.jar --spring.profiles.active=dev

### Prod
java -jar backend.jar --spring.profiles.active=prod


## 2. 프로젝트 구조

- core: 공통 모듈 (auth, security, response, exception, logging, paging)
- domain: 도메인별 비즈니스 로직


## 3. API 응답 규약

{
  "success": true,
  "code": "SUCCESS",
  "message": "요청 성공",
  "data": {}
}


## 4. 인증 방식

- JWT Access + Refresh
- Authorization: Bearer {token}


## 5. 에러 처리

- BasicException 사용
- ErrorCode enum 기반 처리


## 6. 페이징/검색

POST /{domain}/search

Request:
{
  "page": 1,
  "size": 10,
  "condition": {}
}

구조:
PageRequest<T>


## 7. Audit 컬럼

모든 테이블에 아래 컬럼 권장:

- created_at
- created_by
- updated_at
- updated_by


## 8. 환경 분리

- application-local.yml
- application-dev.yml
- application-prod.yml

환경별 실행:

--spring.profiles.active=local/dev/prod


## 9. 환경변수

JWT_SECRET
DB_URL
DB_USERNAME
DB_PASSWORD

.env 파일은 gitignore 필수


## 10. Health Check

GET /health


## 11. 새 도메인 추가 방법

1. domain/{name} 생성
2. 아래 패키지 구성
   - controller
   - service
   - mapper
   - dto/request
   - dto/response
   - entity

3. Mapper XML 생성
4. PageQueryRequest<T> 사용
5. BasicException 사용
6. ApiResponse.result() 사용


## 12. 금지 사항

- entity 직접 반환 금지
- 비밀번호/토큰 로그 출력 금지
- DB 정보 코드에 하드코딩 금지


## 13. 빌드 및 실행

./gradlew build

java -jar build/libs/backend.jar
