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

_NOTE_: It requires JVM v11 or higher