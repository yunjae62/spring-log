# 1. JRE 21 기반 이미지 사용
FROM eclipse-temurin:21-jre

# 2. 타임존 설정 (Asia/Seoul)
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 3. 애플리케이션 디렉토리 생성
WORKDIR /app

# 4. 로그 디렉토리 생성 및 권한 설정
RUN mkdir -p /var/log/app && chmod -R 777 /var/log/app

# 5. JAR 파일 복사 (파일명은 빌드 결과물에 맞게 조정)
COPY build/libs/*.jar app.jar

# 6. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
