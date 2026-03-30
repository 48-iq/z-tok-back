FROM eclipse-temurin:jdk-21-alpine as build

WORKDIR /app

COPY pom.xml .
COPY mvnw .

RUN ./mvnw dependency:resolve

COPY src .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:jre-21-jammy as prod

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]


