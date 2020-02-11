FROM gradle:jdk11 as builder
COPY --chown=gradle:gradle . /home/gradle/
USER gradle
RUN gradle build

FROM adoptopenjdk/openjdk11:jre-11.0.2.9-alpine as runtime
COPY --from=builder /home/gradle/app/build/libs/app-1.0-SNAPSHOT.jar ./app/app.jar
EXPOSE 4567
CMD java -jar ./app/app.jar