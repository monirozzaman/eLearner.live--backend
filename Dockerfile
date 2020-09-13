FROM openjdk:8
ADD target/elearners.live-backend.jar elearners.live-backend.jar
EXPOSE 33002
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","elearners.live-backend.jar"]
