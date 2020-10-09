# Getting started

Start a MongoDB instance on your local machine:
```console
$ cd docker
$ docker-compose up -d
```
Start the agile-metrics backend:
```console
$ ./gradlew clean build
$ java -jar -Dspring.profiles.active=local build/libs/agile-metrics-0.0.1-SNAPSHOT.jar
```

Endpoints Local:
```console
$ curl http://localhost:8080/api/v1/percentiles
$ curl http://localhost:8080/api/v1/cycle-time-scatterplot
$ curl -X POST http://localhost:8080/api/v1/azure/work-items
```

Endpoints cloud:
```console
$ curl http://20.191.55.70:8080/api/v1/percentiles
$ curl http://20.191.55.70:8080/api/v1/cycle-time-scatterplot
$ curl -X POST http://20.191.55.70:8080/api/v1/azure/work-items
```


_NOTE_: It requires JVM v11 or higher