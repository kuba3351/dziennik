FROM maven:3-alpine
COPY . /tmp/dziennik
RUN sed -i 's/localhost/database/g' /tmp/dziennik/src/main/resources/application.properties && cd /tmp/dziennik && mvn install

FROM alpine
RUN apk add openjdk8-jre
COPY --from=0 /tmp/dziennik/target/dziennik-0.0.1-SNAPSHOT.jar /tmp
CMD ["java", "-jar", "/tmp/dziennik-0.0.1-SNAPSHOT.jar"]
