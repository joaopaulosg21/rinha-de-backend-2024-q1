FROM openjdk:17

RUN mkdir /opt/app

COPY ./backend2-0.0.1-SNAPSHOT.jar /opt/app

CMD ["java", "-jar", "/opt/app/backend2-0.0.1-SNAPSHOT.jar"]