# 스프링 부트 로그 처리 및 리트라이/서킷브레이커 적용

## 개요

- logback-spring.xml을 이용한 로그 포맷, 로그 출력 방식(콘솔, 파일), 파일 저장/삭제 정책
    - MDC로 요청 당 고유 ID를 로깅하여 특정 사용자의 요청 로그 구분
    - 일 단위 로그 파일 저장
    - 30일 이후 로그 파일 삭제
    - 로그 파일 저장 경로 설정 (도커 사용 시 볼륨 처리까지)
    - 요청 경로, HTTP 메서드, IP 주소, 실행시간 로깅 (`[200] GET/logs - IP: 127.0.0.1 - 44ms`)
- Resilience4j를 이용한 Retry, circuitbreaker 적용
    - Retry로 총 3번을 0.5초마다 재시도.
    - 재시도 이후로도 에러 발생 시, 서킷브레이커 적용
        - 서킷브레이커가 CLOSED 여도 fallback은 실행

## 사용 방법

### 도커 미사용

1. 맥 기준, `sudo mkdir -p /var/log/app && sudo chmod -R 755 /var/log/app` 로 디렉토리 만들고 접근 권한 추가
1. 클론 후 스프링 실행
2. 10초마다 스케줄러가 로그를 추가함
3. 매 분당 분 단위 파일이 `/var/log/app` 디렉토리에 만들어짐.
    - 로그가 없으면 로그 파일은 만들어지지 않음
    - 보통은 일 단위로 파일을 저장함
4. 루트 디렉토리의 `test.http` 파일로 로그 HTTP 요청을 보내면 MDC requestId 확인 가능 (UUID 8자리)

### 도커 사용

1. 맥 기준, `sudo mkdir -p /var/log/app/docker && sudo chmod -R 755 /var/log/app` 로 디렉토리 만들고 접근 권한 추가
    - 위와 달리 구분을 위해 `docker`를 추가함.
1. 도커 컴포즈로 서비스 실행 (도커파일 기반으로 자동으로 이미지 빌드 후 실행됨)
2. 10초마다 스케줄러가 로그를 추가함
3. 매 분당 분 단위 파일이 `/var/log/app/docker` 디렉토리에 만들어짐.
    - 로그가 없으면 로그 파일은 만들어지지 않음
    - 보통은 일 단위로 파일을 저장함
    - 도커 컴포즈의 volume에서 로컬의 `/var/log/app/docker`와 컨테이너 내의 `/var/log/app`과 매핑됨
4. 루트 디렉토리의 `test.http` 파일로 로그 HTTP 요청을 보내면 MDC requestId 확인 가능 (UUID 8자리)
