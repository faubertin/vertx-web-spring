## Description

Sample RESTful microservice using Vert.x web for the web layer and Spring Boot for the rest (logging, properties, DI etc.).

## Build and run the app

```
./gradlew build
java -jar build/libs/vertx-web-spring-0.0.1-SNAPSHOT.jar
```

## Test the app

```
curl -X POST http://localhost:8080/foos -H "Content-Type: application/json" -d "{\"bar\": \"baz\"}"
curl http://localhost:8080/foos
```