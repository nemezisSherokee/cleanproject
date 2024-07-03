package com.example.orderprocessing.services;

import com.example.infrastructures.entities.WsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RabbitMQListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitAdmin rabbitAdmin;

    @RabbitListener(queues = "wsQueue")
    public void handleMessage(WsMessage message) {
        String queueName = "chat." + message.getDeviceId();

        // Declare the queue if it doesn't exist
        declareQueue(queueName);

        // Send message to WebSocket topic
        String topic = "/topic/" + message.getDeviceId();
        messagingTemplate.convertAndSend(topic, message);
    }

    private Map<String, Boolean> queueDeclaredMap = new HashMap<>();

    private void declareQueue(String queueName) {
        if (!queueDeclaredMap.containsKey(queueName) || !queueDeclaredMap.get(queueName)) {
            rabbitAdmin.declareQueue(new Queue(queueName, true));
            queueDeclaredMap.put(queueName, true);
        }
    }

}
