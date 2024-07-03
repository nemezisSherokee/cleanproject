FROM ubuntu:16.04 AS build
# Install prerequisites
RUN apt-get update && apt-get install -y netcat

FROM openjdk:17

# Copy netcat from the build stage
COPY --from=build /bin/nc /bin/nc

COPY target/apigateway-0.0.1-SNAPSHOT.jar app.jar

ADD entrypoint.sh entrypoint.sh
CMD [ "entrypoint.sh" ]
ENTRYPOINT ["sh"]
