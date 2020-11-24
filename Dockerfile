FROM maven:3.6-adoptopenjdk-11
COPY . /tmp/dziennik
RUN cd /tmp/dziennik && mvn install

FROM alpine
RUN apk add openjdk9-jre
COPY --from=0 /tmp/dziennik/target/dziennik-0.0.1-SNAPSHOT.jar /tmp
CMD ["java", "-jar", "/tmp/dziennik-0.0.1-SNAPSHOT.jar"]
