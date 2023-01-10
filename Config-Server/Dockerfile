FROM openjdk:17 as builder

WORKDIR /app/Config-Server

COPY ./pom.xml /app
COPY ./Config-Server/.mvn ./.mvn
COPY ./Config-Server/mvnw .
COPY ./Config-Server/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Config-Server/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Config-Server/target/Config-Server-0.0.1-SNAPSHOT.jar .
EXPOSE 9010

CMD ["java", "-jar", "Config-Server-0.0.1-SNAPSHOT.jar"]
