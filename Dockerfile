FROM adoptopenjdk:17-jre-hotspot

ARG JAR_FILE=target/*.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

EXPOSE 8001

ENTRYPOINT ["java","-jar","/app/app.jar"]

