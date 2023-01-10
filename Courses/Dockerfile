FROM openjdk:17 as builder

WORKDIR /app/Courses

COPY ./pom.xml /app
COPY ./Courses/.mvn ./.mvn
COPY ./Courses/mvnw .
COPY ./Courses/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./Courses/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Courses/target/Courses-0.0.1-SNAPSHOT.jar .
EXPOSE 8081

CMD ["java", "-jar", "Courses-0.0.1-SNAPSHOT.jar"]
