FROM openjdk:17 as builder

WORKDIR /app/Subscription

COPY ./pom.xml /app
COPY ./Subscription/.mvn ./.mvn
COPY ./Subscription/mvnw .
COPY ./Subscription/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Subscription/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Subscription/target/Subscription-0.0.1-SNAPSHOT.jar .
EXPOSE 8086

CMD ["java", "-jar", "Subscription-0.0.1-SNAPSHOT.jar"]
