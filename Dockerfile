FROM openjdk:17-oracle
COPY target/*.jar backingService.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/backingService.jar"]