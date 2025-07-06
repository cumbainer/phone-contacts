# ---------- build stage ----------
FROM maven:3.9.8-eclipse-temurin-17-alpine AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests   # produces target/*.jar

# ---------- runtime stage ----------
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /workspace/target/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
