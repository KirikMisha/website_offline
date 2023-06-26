#содержит образ из которого собираем проект JDK11 т.к. java 11
FROM openjdk:11
RUN apt-get update -y
RUN apt-get install maven -y
WORKDIR /app
#RUN cd app
COPY . .
RUN mvn install
RUN mvn clean
ADD /target/test-3-2-1.0-SNAPSHOT.jar backend.jar
CMD ["java", "-jar", "backend.jar"]
#CMD ["java", "-jar", "test-3-2-1.0-SNAPSHOT.jar"]
#содержит описание как собрать контейнер
