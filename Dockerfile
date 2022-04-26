FROM adoptopenjdk:11-jre-hotspot as builder
ARG JAR_FILE=target/*.jar
ARG DB_FILE=teldatest.sqlite
COPY ${JAR_FILE} application.jar
COPY ${DB_FILE} teldatest.sqlite
ENTRYPOINT ["java","-jar","/application.jar"]