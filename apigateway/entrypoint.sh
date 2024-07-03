#!/bin/bash

while :
do
  status_code=$(curl --write-out %{http_code} --silent --output /dev/null http://eurekaserver:8761)

  echo "Eureka service response: $status_code\n"

  if [ $status_code -eq 200 ]
  then

	set -eu

	echo "Checking DB connection ..."

	i=0
	until [ $i -ge 10 ]
	do
	  nc -z postgres 5432 && break

	  i=$(( i + 1 ))

	  echo "$i: Waiting for DB 1 second ..."
	  sleep 1
	done

	if [ $i -eq 10 ]
	then
	  echo "DB connection refused, terminating ..."
	  exit 1
	fi

	echo "DB is up ..."
    java -jar -Dspring.profiles.active=docker app.jar
    break
  fi

	echo "sleep 3 seconds ..."

  sleep 3
done