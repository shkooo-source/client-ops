# 1단계: 빌드 (Maven 사용)
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# 최상단에 있는 백엔드 파일들을 복사
COPY pom.xml .
COPY src ./src

# 빌드 실행
RUN mvn clean package -DskipTests

# 2단계: 실행 (안정적인 Eclipse Temurin JDK 사용)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 빌드 결과물 복사
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]