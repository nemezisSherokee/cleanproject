# Spring Boot Complex Exercises

## Introduction

Welcome to the Spring Boot Complex Exercises project! This collection of exercises is designed to deepen your understanding of Spring Boot, a powerful framework for building Java applications. Through these exercises, you will explore a variety of advanced topics and gain hands-on experience with building complex, scalable, and secure applications.

The exercises are structured to cover a wide range of features and best practices in Spring Boot development, from creating microservices and implementing security measures to handling asynchronous processing and optimizing performance. Whether you are a beginner looking to expand your knowledge or an experienced developer seeking to refine your skills, these exercises will provide valuable insights and practical experience.

## Project Overview

The project consists of the following exercises:

### 1. Microservices Architecture
- **Objective**: Design a microservices architecture using Spring Boot.
- **Tasks**:
  - Create services for product catalog, and order processing.
  - Implement service discovery using Eureka.
  - Implement an API gateway .
  - Use Spring Cloud Config Server for externalized configuration management. (done in another exercise)

### 2. Security and Authentication (no done since the actual modern auth is handled by a api gateway)
- **Objective**: Secure your Spring Boot application.
- **Tasks**:
  - Use Spring Security with JWT for authentication.
  - Implement role-based access control (admin, user).
  - Integrate OAuth2 for third-party authentication (e.g., Google, GitHub).

### 3. Asynchronous Processing
- **Objective**: Implement asynchronous messaging.
- **Tasks**:
  - Use RabbitMQ or Kafka with Spring Boot.
  - Create a publisher service that publishes an event (e.g., order placed).
  - Create a subscriber service that processes the event.

### 4. Database Operations
- **Objective**: Perform database operations using Spring Data JPA.
- **Tasks**:
  - Perform CRUD operations on a MySQL or PostgreSQL database.
  - Implement advanced querying using JPQL or native queries.
  - Use Spring Data Redis for caching frequently accessed data.

### 5. Testing
- **Objective**: Write tests for your Spring Boot application.
- **Tasks**:
  - Write unit tests and integration tests using JUnit and Mockito.
  - Test RESTful APIs using Spring MVC Test framework.
  - Use Testcontainers for integration tests with databases running in Docker containers.

### 6. Monitoring and Logging
- **Objective**: Integrate monitoring and logging in your Spring Boot application.
- **Tasks**:
  - Use Spring Boot Actuator for monitoring.
  - Export metrics to Prometheus using Micrometer.
  - Configure centralized logging with ELK stack (Elasticsearch, Logstash, Kibana) or Splunk.

### 7. Performance Optimization
- **Objective**: Optimize the performance of your Spring Boot application.
- **Tasks**:
  - Implement caching using EhCache or Redis.

### 8. WebSockets and Real-Time Communication
- **Objective**: Implement real-time communication using WebSockets.
- **Tasks**:
  - Use Spring WebSockets.
  - Build a real-time chat application with WebSocket endpoints.

### 9. Deployment and DevOps
- **Objective**: Deploy your Spring Boot application using modern DevOps practices.
- **Tasks**:
  - Containerize your application using Docker.
  - Create CI/CD pipelines with Jenkins or GitLab CI/CD.
  - Deploy to a cloud platform  using Kubernetes.

### 10. Advanced Topics
- **Objective**: Explore advanced Spring Boot topics.
- **Tasks**:
  - Implement distributed transactions with JTA. (done in another project)
  - Use Spring Batch for batch processing. (done in another project)
  - Explore reactive programming with Spring WebFlux and Reactive Spring Data.

### Starting DOCKER COMPOSE
    * docker compose build 
    * docker compose up -d
    * docker compose down
    * docker compose up -d --force-recreate zookeeper kafka
    * sh restart-docker-container.sh
    * docker logs productcatalog-container -f
    * docker logs oderprocessing-container -f
    * docker logs apigateway-container -f

### Test the Setup
after the docker containers have all started, 

* run the following api commands many times:
    curl -X POST http://localhost:9001/productcatalog/api/v1.0/ -H "Content-Type: application/json" -d '"good morning"'`
    
    
    the product catalog service will be printing a message of type: 
    `I have send a message via RabbitMQ and received a response via Kafkaat least 4 message received last message is: "good morning mon frere"
    `
* run the following get command:
    `http://localhost:9001/orderprocessing/api/v1.0/orders/Salome Schultz`
    The orderprocessing service will respond. The first time we will be waiting for 3 second. the response will then be cached in redis
* run the following get command:
    `http://localhost:9001/orderprocessing/api/v1.0/orders/since/yersterday`
    The orderprocessing service will respond.  we will be waiting for 3 second. the response will NOT be cached in redis
   
#####NOTE: http://localhost:9001 is the api gateway