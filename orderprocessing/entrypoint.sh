#!/bin/bash

while :; do
  status_code=$(curl --write-out %{http_code} --silent --output /dev/null http://eurekaserver:8761)
  echo "Eureka service response: $status_code"

  if [ "$status_code" -eq 200 ]; then
    set -eu

    echo "Checking DB, Kafka, RabbitMQ, Redis connection ..."

    i=0
    while [ "$i" -lt 20 ]; do
      postgres_up=$(nc -z postgres 5432 && echo "0" || echo "1")
      kafka_up=$(nc -z kafka 29092 && echo "0" || echo "1")
      rabbitmq_up=$(nc -z rabbitmq 5672 && echo "0" || echo "1")
      redis_up=$(nc -z redis 6379 && echo "0" || echo "1")

      if [ "$postgres_up" -eq 0 ] && [ "$kafka_up" -eq 0 ] && [ "$rabbitmq_up" -eq 0 ] && [ "$redis_up" -eq 0 ]; then
        break
      fi

      if [ "$postgres_up" -ne 0 ]; then
        echo "Postgres is not up."
      fi
      if [ "$kafka_up" -ne 0 ]; then
        echo "Kafka is not up."
      fi
      if [ "$rabbitmq_up" -ne 0 ]; then
        echo "RabbitMQ is not up."
      fi
      if [ "$redis_up" -ne 0 ]; then
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
    java -jar -Dspring.profiles.active=docker app.jar
    break
  fi

  echo "Sleeping for 3 seconds ..."
  sleep 3
done
