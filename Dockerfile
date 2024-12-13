FROM openjdk:23-jdk AS builder

ARG COMPILE_DIR=/code_folder

WORKDIR ${COMPILE_DIR}

COPY .mvn .mvn
COPY src src
COPY pom.xml .
COPY mvnw . 
COPY mvnw.cmd .

RUN ./mvnw clean package -Dmaven.tests.skip=true

FROM openjdk:23-jdk

ARG DEPLOY_DIR=/app

WORKDIR ${DEPLOY_DIR}

COPY --from=builder /code_folder/target/noticeboard-0.0.1-SNAPSHOT.jar vttp5a-ssf-assessment-app.jar

# RUN apt update && apt install -y curl

ENV PORT=8080

EXPOSE ${PORT}

HEALTHCHECK --interval=60s --start-period=120s CMD curl -s -f http://localhost:${PORT}/status || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar vttp5a-ssf-assessment-app.jar