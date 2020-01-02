FROM gradle:jdk11 as builder
USER root
COPY . ./
RUN gradle build

FROM adoptopenjdk/openjdk11:jre-11.0.2.9-alpine as runtime
COPY --from=builder /home/gradle/rmp/build/libs/rmp-1.0-SNAPSHOT.jar ./app/app.jar
EXPOSE 4567
CMD java -jar ./app/app.jar