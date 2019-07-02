FROM maven as build

WORKDIR /usr/src/app

COPY pom.xml .
COPY . .

RUN rm ./java/api-phone/pom.xml
RUN mv ./java/api-phone/pom.ci.xml ./java/api-phone/pom.xml
RUN mvn clean install

FROM jetty

COPY --from=build /usr/src/app/java/api-phone/target/webapi-1.0-SNAPSHOT.war /var/lib/jetty/webapps/root.war
EXPOSE 8080