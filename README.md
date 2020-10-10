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
$ curl -X POST http://localhost:8080/api/v1/azure/work-items --header "Content-Type: application/json" --data '{ "organization": "Actionable-Agile-Metrics", "project": "Actionable Agile Metrics", "username":"test-api-readonly", "password":"ryt2h45vr5huolv6sqfnf2y3ctei3o6ng4qwcb2bkbkayfzxtgla"}'
```

Endpoints cloud:
```console
$ curl http://20.191.55.70:8080/api/v1/percentiles
$ curl http://20.191.55.70:8080/api/v1/cycle-time-scatterplot
$ curl -X POST http://20.191.55.70:8080/api/v1/azure/work-items --header "Content-Type: application/json" --data '{ "organization": "Actionable-Agile-Metrics", "project": "Actionable Agile Metrics", "username":"test-api-readonly", "password":"ryt2h45vr5huolv6sqfnf2y3ctei3o6ng4qwcb2bkbkayfzxtgla"}'

```


_NOTE_: It requires JVM v11 or higher