#содержит образ из которого собираем проект JDK11 т.к. java 11
FROM openjdk:11
RUN apt-get update -y
RUN apt-get install maven -y
WORKDIR /app
#RUN cd app
COPY . .
RUN mvn install
RUN #mvn clean
ADD /target/test-3-2-1.0-SNAPSHOT.jar backend.jar
CMD ["java", "-jar", "backend.jar"]
#CMD ["java", "-jar", "test-3-2-1.0-SNAPSHOT.jar"]
#содержит описание как собрать контейнер

#FROM maven:3.5.0-jdk-8-alpine AS builder
#
#ADD ./pom.xml pom.xml
#ADD ./src src/
#
#RUN mvn clean package
#
#From openjdk:8-jre-alpine
#
#COPY --from=builder /target/test-3-2-1.0-SNAPSHOT.jar backend.jar
#
#EXPOSE 8080
#
#CMD ["java", "-jar", "backend.jar"]