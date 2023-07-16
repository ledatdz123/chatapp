package com.example.chatapp.controller;

import com.example.chatapp.dto.request.SendMessageRequest;
import com.example.chatapp.dto.response.ApiResponse;
import com.example.chatapp.exception.ChatException;
import com.example.chatapp.exception.MessageException;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.services.MessageService;
import com.example.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageRequest(@RequestBody SendMessageRequest red,
                                                      Authentication authentication)
            throws UserException, ChatException {
        UserApp userApp=userService.getProfile(authentication);
        red.setUserId(userApp.getUserId());
        Message message=messageService.sendMessage(red);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
    @PostMapping("/createImg")
    public ResponseEntity<Message> sendMessageImageRequest(@RequestBody SendMessageRequest red,
                                                      Authentication authentication)
            throws UserException, ChatException {
        UserApp userApp=userService.getProfile(authentication);
        red.setUserId(userApp.getUserId());
        Message message=messageService.sendMessageImage(red);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessage(@PathVariable Integer chatId,
                                                        Authentication authentication)
            throws UserException, ChatException {
        UserApp userApp=userService.getProfile(authentication);
        List<Message> message=messageService.getChatMessage(chatId, userApp);
        return new ResponseEntity<List<Message>>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{messId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer messId,
                                                         Authentication authentication)
            throws UserException, MessageException {
        UserApp redUser=userService.getProfile(authentication);
        messageService.deleteMesage(messId, redUser);
        ApiResponse res=new ApiResponse("chat delete", false);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
