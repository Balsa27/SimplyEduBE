FROM openjdk:17 as builder

WORKDIR /app/Discovery-Service

COPY ./pom.xml /app
COPY ./Discovery-Service/.mvn ./.mvn
COPY ./Discovery-Service/mvnw .
COPY ./Discovery-Service/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Discovery-Service/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Discovery-Service/target/Discovery-Service-0.0.1-SNAPSHOT.jar .
EXPOSE 9001

CMD ["java", "-jar", "Discovery-Service-0.0.1-SNAPSHOT.jar"]
