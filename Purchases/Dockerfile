FROM openjdk:17 as builder

WORKDIR /app/Purchases

COPY ./pom.xml /app
COPY ./Purchases/.mvn ./.mvn
COPY ./Purchases/mvnw .
COPY ./Purchases/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Purchases/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Purchases/target/Purchases-0.0.1-SNAPSHOT.jar .
EXPOSE 8087

CMD ["java", "-jar", "Purchases-0.0.1-SNAPSHOT.jar"]
