package com.example.chatapp.services;

import com.example.chatapp.dto.request.SendMessageRequest;
import com.example.chatapp.exception.ChatException;
import com.example.chatapp.exception.MessageException;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.UserApp;

import java.util.List;

public interface MessageService {
    public Message sendMessage(SendMessageRequest red) throws UserException, ChatException;
    public Message sendMessageImage(SendMessageRequest red) throws UserException, ChatException;
    public List<Message> getChatMessage(Integer chatId, UserApp redUser) throws UserException, ChatException;
    public Message findMessageById(Integer messageId) throws MessageException;
    public void deleteMesage(Integer messageId, UserApp redUser) throws UserException, MessageException;
}
