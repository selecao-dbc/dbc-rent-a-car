FROM openjdk:8
ADD target/rent-a-car-0.0.1-SNAPSHOT.jar /
EXPOSE 8282
ENTRYPOINT ["java", "-jar", "rent-a-car-0.0.1-SNAPSHOT.jar"]