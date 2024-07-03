package com.example.productcatalog.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "myTopic", groupId = "myGroup")
    public void receiveMessage(String message) {
        System.out.println("I have send a message via RabbitMQ " +
                "and received a response via Kafka" +
                 message);
    }

}
