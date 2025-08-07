FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build
COPY . .
RUN mvn -B dependency:go-offline -Dmaven.test.skip=true
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /build/infra/config-server/target/*.jar ./app.jar
COPY --from=builder /build/infra/config-server/src/main/resources/application-docker.yml ./application.yml
COPY --from=builder /build/infra/config-server/src/main/resources/config ./config/

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]