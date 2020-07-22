FROM openjdk:8-alpine

LABEL author="Roberto Rocha"
LABEL email="rrocha.roberto@gmail.com"
LABEL data="20/07/2020"

WORKDIR /usr/local/bin

#Transfer the war file to imagem
COPY ./student-backend-1.0.jar student-backend.jar

#execute java application inside the container
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "student-backend.jar"]
