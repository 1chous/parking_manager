FROM gradle:9.5.1-jdk21-alpine AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY gradlew ./

RUN chmod +x ./gradlew

RUN gradle dependencies

COPY src ./src

RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jre-noble
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]