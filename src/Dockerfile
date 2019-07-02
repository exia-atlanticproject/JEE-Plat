FROM maven as build


WORKDIR /usr/src/app

COPY pom.xml .
#RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline
COPY . .

RUN rm ./java/api-phone/pom.xml
RUN mv ./java/api-phone/pom.ci.xml ./java/api-phone/pom.xml
RUN mvn clean install

FROM jetty

COPY --from=build /usr/src/app/java/api-phone/target/webapi-1.0-SNAPSHOT.war /var/lib/jetty/webapps/root.war
EXPOSE 8080