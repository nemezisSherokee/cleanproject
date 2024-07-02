package com.example.orderprocessing.controllers;

import com.example.infrastructures.entities.WsMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat.send")
    @SendTo("/orderservicetopic/messages")
    public WsMessage sendMessage(WsMessage message) {
        return message;
    }

}
