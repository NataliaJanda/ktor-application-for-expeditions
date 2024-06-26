FROM openjdk:17-jdk-slim
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/ktor-docker-sample.jar
ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]