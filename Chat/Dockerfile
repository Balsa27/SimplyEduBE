FROM openjdk:17 as builder

WORKDIR /app/Chat

COPY ./pom.xml /app
COPY ./Chat/.mvn ./.mvn
COPY ./Chat/mvnw .
COPY ./Chat/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Chat/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Chat/target/Chat-0.0.1-SNAPSHOT.jar .
EXPOSE 8090

CMD ["java", "-jar", "Chat-0.0.1-SNAPSHOT.jar"]
