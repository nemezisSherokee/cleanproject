FROM openjdk:17-jdk-slim-buster

COPY target/eurekaserver-0.0.1-SNAPSHOT.jar service-discovery.jar

ADD entrypoint.sh entrypoint.sh
CMD [ "entrypoint.sh" ]
ENTRYPOINT ["sh"]
