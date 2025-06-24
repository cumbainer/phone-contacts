# -------- build stage --------
FROM maven:3.9.7-eclipse-temurin-17 AS build
WORKDIR /app

# copy project and compile
COPY pom.xml .
COPY src ./src
RUN mvn -B package -DskipTests

# -------- runtime stage --------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# non-root user for safer runtime
RUN adduser -D spring
USER spring

# copy the fat-jar produced in build stage
COPY --from=build /app/target/*jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
