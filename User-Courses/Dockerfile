FROM openjdk:17 as builder

WORKDIR /app/User-Courses

COPY ./pom.xml /app
COPY ./User-Courses/.mvn ./.mvn
COPY ./User-Courses/mvnw .
COPY ./User-Courses/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./User-Courses/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/User-Courses/target/User-Courses-0.0.1-SNAPSHOT.jar .
EXPOSE 8083

CMD ["java", "-jar", "User-Courses-0.0.1-SNAPSHOT.jar"]
