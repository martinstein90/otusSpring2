FROM openjdk:8-jdk

ENV spring.datasource.username=root
ENV spring.datasource.password=root

WORKDIR /app

COPY superjar.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar" ]