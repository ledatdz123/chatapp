package com.example.chatapp.services;

import com.example.chatapp.dto.request.GroupChatRequest;
import com.example.chatapp.exception.ChatException;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.Chat;
import com.example.chatapp.model.UserApp;

import java.util.List;

public interface ChatService {
    public Chat createChat(UserApp redUser, Integer useId2) throws UserException;
    public Chat findChatById(Integer chatId) throws ChatException;
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
    public Chat createGroup(GroupChatRequest red, UserApp redUser) throws UserException;
    public Chat addUserToGroup(Integer uId, Integer chatId, UserApp redUser) throws UserException, ChatException;
    public Chat renameGroup(Integer chatId, String groupName, UserApp redUser) throws ChatException, UserException;
    public Chat removeFromGroup(Integer chatId, Integer userId, UserApp redUser) throws ChatException, UserException;
    public void deleteChat(Integer chatId, Integer uId) throws UserException, ChatException;
}
