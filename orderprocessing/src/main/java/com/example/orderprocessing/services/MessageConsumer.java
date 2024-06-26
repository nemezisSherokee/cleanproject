package com.example.orderprocessing.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class MessageConsumer {

    private CountDownLatch latch = new CountDownLatch(4);

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        latch.countDown();
        if (latch.getCount() == 0){
            System.out.println("Received message: " + message);
        }
    }
}