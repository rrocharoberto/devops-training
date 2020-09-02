FROM tomcat:9.0.37-jdk8-openjdk-slim-buster

LABEL author="Roberto Rocha"
LABEL email="rrocha.roberto@gmail.com"
LABEL data="01/09/2020"

# Create the conf directory
RUN mkdir -p /usr/local/tomcat/conf
RUN mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps2
RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
RUN echo "spring.profiles.active=docker" >> /usr/local/tomcat/conf/catalina.properties

# Copy configurations (Tomcat users, Manager app)
COPY tomcat-users.xml /usr/local/tomcat/conf/
COPY context.xml /usr/local/tomcat/webapps/manager/META-INF/
COPY context.xml /usr/local/tomcat/webapps.dist/manager/META-INF/




