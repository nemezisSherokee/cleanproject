#!/bin/bash

# Default values for environment variables
CHECK_POSTGRES=${CHECK_POSTGRES:-true}
CHECK_KAFKA=${CHECK_KAFKA:-true}
CHECK_RABBITMQ=${CHECK_RABBITMQ:-true}
CHECK_REDIS=${CHECK_REDIS:-true}
CHECK_REDIS_HOST=${CHECK_REDIS_HOST}
CHECK_EUREKA_SERVER=${CHECK_EUREKA_SERVER:-true}

KAFKA_PORT=${KAFKA_PORT}
REDIS_PORT=${REDIS_PORT}
RABBIT_PORT=${RABBIT_PORT}
POSTGRES_PORT=${POSTGRES_PORT}
EUREKA_PORT=${EUREKA_PORT}

while :; do

    if [ "$CHECK_EUREKA_SERVER" != "false" ]; then
      while :; do
        status_code=$(curl --write-out %{http_code} --silent --output /dev/null http://eurekaserver:$EUREKA_PORT)
        echo "Eureka service response: $status_code"

        if [ "$status_code" -eq 200 ]; then
          break
        fi

        echo "Sleeping for 3 seconds ..."
        sleep 3
      done
    fi


    set -eu

    echo "Checking DB, Kafka, RabbitMQ, Redis connection ..."

    i=0
    while [ "$i" -lt 20 ]; do
      postgres_up=0
      kafka_up=0
      rabbitmq_up=0
      redis_up=0

      if [ "$CHECK_POSTGRES" != "false" ]; then
        postgres_up=$(nc -z postgresql $POSTGRES_PORT && echo "0" || echo "1")
      fi
      if [ "$CHECK_KAFKA" != "false" ]; then
        kafka_up=$(nc -z kafka $KAFKA_PORT && echo "0" || echo "1")
      fi
      if [ "$CHECK_RABBITMQ" != "false" ]; then
        rabbitmq_up=$(nc -z rabbitmq $RABBIT_PORT && echo "0" || echo "1")
      fi
      if [ "$CHECK_REDIS" != "false" ]; then
        redis_up=$(nc -z $CHECK_REDIS_HOST $REDIS_PORT && echo "0" || echo "1")
      fi

      if [ "$postgres_up" -eq 0 ] && [ "$kafka_up" -eq 0 ] && [ "$rabbitmq_up" -eq 0 ] && [ "$redis_up" -eq 0 ]; then
        break
      fi

      if [ "$CHECK_POSTGRES" != "false" ] && [ "$postgres_up" -ne 0 ]; then
        echo "Postgres is not up."
      fi
      if [ "$CHECK_KAFKA" != "false" ] && [ "$kafka_up" -ne 0 ]; then
        echo "Kafka is not up."
      fi
      if [ "$CHECK_RABBITMQ" != "false" ] && [ "$rabbitmq_up" -ne 0 ]; then
        echo "RabbitMQ is not up."
      fi
      if [ "$CHECK_REDIS" != "false" ] && [ "$redis_up" -ne 0 ]; then
        echo "Redis is not up."
      fi

      i=$((i + 1))
      echo "$i: Waiting for services (DB, Kafka, RabbitMQ, Redis) for 1 second ..."
      sleep 1
    done

    if [ "$i" -eq 20 ]; then
      echo "One or more services (DB, Kafka, RabbitMQ, Redis) connection refused, terminating ..."
      exit 1
    fi

    echo "All services (DB, Kafka, RabbitMQ, Redis) are up ..."
    java -jar -Dspring.profiles.active=docker,redis app.jar
    break

  echo "Sleeping for 3 seconds ..."
  sleep 3
done
