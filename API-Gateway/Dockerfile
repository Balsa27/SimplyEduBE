FROM openjdk:17 as builder

WORKDIR /app/API-Gateway

COPY ./pom.xml /app
COPY ./API-Gateway/.mvn ./.mvn
COPY ./API-Gateway/mvnw .
COPY ./API-Gateway/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./API-Gateway/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/API-Gateway/target/API-Gateway-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

CMD ["java", "-jar", "API-Gateway-0.0.1-SNAPSHOT.jar"]
