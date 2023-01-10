FROM openjdk:17 as builder

WORKDIR /app/Statistics

COPY ./pom.xml /app
COPY ./Statistics/.mvn ./.mvn
COPY ./Statistics/mvnw .
COPY ./Statistics/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Statistics/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Statistics/target/Statistics-0.0.1-SNAPSHOT.jar .
EXPOSE 8085

CMD ["java", "-jar", "Statistics-0.0.1-SNAPSHOT.jar"]
