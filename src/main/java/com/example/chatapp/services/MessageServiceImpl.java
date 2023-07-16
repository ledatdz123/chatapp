package com.example.chatapp.services;

import com.example.chatapp.dto.request.SendMessageRequest;
import com.example.chatapp.exception.ChatException;
import com.example.chatapp.exception.MessageException;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.Chat;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Override
    public Message sendMessage(SendMessageRequest red) throws UserException, ChatException {
        UserApp userApp=userService.finUserById(red.getUserId());
        Chat chat=chatService.findChatById(red.getChatId());
        Message message=new Message();
        message.setChat(chat);
        message.setUserApp(userApp);
        message.setImage(false);
        message.setContent(red.getContent());
        message.setTimestamp(LocalDateTime.now());
        this.messageRepo.save(message);
        return message;
    }

    @Override
    public Message sendMessageImage(SendMessageRequest red) throws UserException, ChatException {
        UserApp userApp=userService.finUserById(red.getUserId());
        Chat chat=chatService.findChatById(red.getChatId());
        Message message=new Message();
        message.setChat(chat);
        message.setUserApp(userApp);
        message.setImage(true);
        message.setContent(red.getContent());
        message.setTimestamp(LocalDateTime.now());
        this.messageRepo.save(message);
        return message;
    }

    @Override
    public List<Message> getChatMessage(Integer chatId, UserApp redUser) throws UserException, ChatException {
        Chat chat=chatService.findChatById(chatId);
        if (!chat.getUsers().contains(redUser)){
            throw new UserException("you are not releted to this chat"+chat.getId());
        }
        List<Message> messages=messageRepo.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt=messageRepo.findById(messageId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new MessageException("message not found with id"+messageId);
    }

    @Override
    public void deleteMesage(Integer messageId, UserApp redUser) throws UserException, MessageException {
        Message message=findMessageById(messageId);
        if (message.getUserApp().getUserId().equals(redUser.getUserId())){
            messageRepo.deleteById(messageId);
        }
        throw new UserException("you cant delete anothe user's message"+redUser.getUsername());
    }
}
