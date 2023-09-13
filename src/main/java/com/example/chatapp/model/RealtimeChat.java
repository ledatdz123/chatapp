package com.example.chatapp.model;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealtimeChat {
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    private Message receivePublicMessage(@Payload Message message) {
        return message;
    }
    @MessageMapping("/private-message")
    @SendTo("")
    private Message receivePrivateMessage(@Payload Message message) {
        this.simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getChat().getId()), "/private", message);
        return message;
    }
}
