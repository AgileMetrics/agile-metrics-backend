FROM openjdk:11-jdk-slim AS builder
WORKDIR /home/root/agile-metrics-build/
COPY . .
RUN ./gradlew clean build

FROM openjdk:11-jdk-slim
WORKDIR /home/root/agile-metrics-app/
COPY --from=builder /home/root/agile-metrics-build/build/libs/agile-metrics*.jar /home/root/agile-metrics-app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

