FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*
RUN wget -O /sentinel-dashboard.jar \
    https://github.com/alibaba/Sentinel/releases/download/1.8.9/sentinel-dashboard-1.8.9.jar
EXPOSE 8858
ENTRYPOINT ["java", "-Dserver.port=8858", "-jar", "/sentinel-dashboard.jar"]
