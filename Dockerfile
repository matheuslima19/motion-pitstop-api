# Etapa de construção
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/pitstop-api.jar /app/pitstop-api.jar
ENTRYPOINT ["java", "-jar", "/app/pitstop-api.jar"]
