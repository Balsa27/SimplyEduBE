FROM openjdk:17 as builder

WORKDIR /app/Cart

COPY ./pom.xml /app
COPY ./Cart/.mvn ./.mvn
COPY ./Cart/mvnw .
COPY ./Cart/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Cart/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Cart/target/Cart-0.0.1-SNAPSHOT.jar .
EXPOSE 8082

CMD ["java", "-jar", "Cart-0.0.1-SNAPSHOT.jar"]
