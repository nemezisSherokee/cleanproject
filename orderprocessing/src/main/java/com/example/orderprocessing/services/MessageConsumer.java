package com.example.orderprocessing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class MessageConsumer {

    private static int MaxMessageCount = 4;
    private CountDownLatch latch = new CountDownLatch(MaxMessageCount);
    private final MessageProducer messageProducer;

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        latch.countDown();
        if (latch.getCount() == 0){
            System.out.println("Received message: " + message);
            messageProducer.sendMessage(String.format("at least %s message received last message is: %s", MaxMessageCount, message));
        }
    }
}