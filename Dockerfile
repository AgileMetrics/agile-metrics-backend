FROM openjdk:11.0.8-jdk
COPY build/libs/agile-metrics*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
