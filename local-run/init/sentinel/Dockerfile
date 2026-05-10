FROM maven:3.9-eclipse-temurin-21
WORKDIR /app
COPY sentinel-dashboard-1.8.9.jar /app/sentinel-dashboard.jar
EXPOSE 8858
ENTRYPOINT ["java", "-Dserver.port=8858", "-jar", "/app/sentinel-dashboard.jar"]
