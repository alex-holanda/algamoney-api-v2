# Build
FROM maven:3-openjdk-11 as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn package

# Final
FROM openjdk:11-jre
EXPOSE 8080
CMD exec java $JAVA_OPTS -Xverify:none -XX:TieredStopAtLevel=1 -jar /app.jar
COPY --from=build /build/target/*.jar /app.jar
