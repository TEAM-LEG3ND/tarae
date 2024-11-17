# 첫 번째 스테이지: Gradle을 사용하여 애플리케이션 빌드
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# 소스 코드 및 Gradle 래퍼를 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# 의존성을 다운로드하고 애플리케이션 빌드
RUN ./gradlew bootJar --no-daemon

# 레이어드 JAR 파일을 추출
RUN mkdir -p target/dependency && (cd target/dependency; java -Djarmode=layertools -jar ../../build/libs/*.jar extract)

# 두 번째 스테이지: 실제 실행할 이미지 생성
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# 레이어 별로 파일들을 복사
COPY --from=build /workspace/app/target/dependency/dependencies/ ./
COPY --from=build /workspace/app/target/dependency/spring-boot-loader/ ./
COPY --from=build /workspace/app/target/dependency/snapshot-dependencies/ ./
COPY --from=build /workspace/app/target/dependency/application/ ./

# 애플리케이션 실행
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]