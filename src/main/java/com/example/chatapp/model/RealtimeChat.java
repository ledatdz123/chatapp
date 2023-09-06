package com.example.chatapp.model;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class RealtimeChat {
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message recieveMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSend("/group"+message.getChat().getId().toString(), message);
        return message;
    }
}