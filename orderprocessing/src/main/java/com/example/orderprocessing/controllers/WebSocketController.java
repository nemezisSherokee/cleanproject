package com.example.orderprocessing.controllers;

import com.example.infrastructures.entities.WsMessage;
import com.example.orderprocessing.services.RabbitMQListener;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitMQListener rabbitMQListener;

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public WsMessage sendMessage(WsMessage message) {
        return message;
    }

    @MessageMapping("/chat.unknown.send")
    public void unknownSendMessage(WsMessage message) {
        String topic = decideTopic(message);
        messagingTemplate.convertAndSend("/topic/" + topic, message);
    }

    @MessageMapping("/chat.device.send")
    public void receiveMessageAndResentToDeviceId(WsMessage message) {
        rabbitMQListener.handleMessage(message);
    }

    private String decideTopic(WsMessage message) {
        // Example logic to decide the topic
        if (message.getText().contains("urgent")) {
            return "urgent";
        } else {
            return "general";
        }
    }

}
