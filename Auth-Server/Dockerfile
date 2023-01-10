FROM openjdk:17 as builder

WORKDIR /app/Auth-Server

COPY ./pom.xml /app
COPY ./Auth-Server/.mvn ./.mvn
COPY ./Auth-Server/mvnw .
COPY ./Auth-Server/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Auth-Server/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Auth-Server/target/Auth-Server-0.0.1-SNAPSHOT.jar .
EXPOSE 9002

CMD ["java", "-jar", "Auth-Server-0.0.1-SNAPSHOT.jar"]
