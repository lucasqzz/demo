FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY settings.xml /root/.m2/settings.xml
COPY pom.xml ./
COPY common/pom.xml ./common/
COPY gateway-service/pom.xml ./gateway-service/
COPY user-service/pom.xml ./user-service/
COPY product-service/pom.xml ./product-service/
COPY order-service/pom.xml ./order-service/
COPY common/src ./common/src
COPY product-service/src ./product-service/src
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn clean package -pl product-service -am -DskipTests

FROM maven:3.9-eclipse-temurin-21
WORKDIR /app
COPY --from=build /app/product-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
