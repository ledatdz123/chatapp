package com.example.chatapp.controller;

import com.example.chatapp.dto.request.GroupChatRequest;
import com.example.chatapp.dto.request.SingleChatRequest;
import com.example.chatapp.dto.response.ApiResponse;
import com.example.chatapp.exception.ChatException;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.Chat;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.services.ChatService;
import com.example.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChat,
                                                  Authentication authentication)
            throws UserException {
        UserApp redUser=userService.getProfile(authentication);
        Chat chat=chatService.createChat(redUser, singleChat.getUserId());
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest red,
                                                   Authentication authentication)
            throws UserException {
        UserApp redUser=userService.getProfile((authentication));
        Chat chat=chatService.createGroup(red, redUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Chat>> getChatUser(Authentication authentication)
            throws UserException {
        UserApp redUser=userService.getProfile(authentication);
        List<Chat> chats=chatService.findAllChatByUserId(redUser.getUserId());
        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
    }
    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,
                                                      @PathVariable Integer userId,
                                                      Authentication authentication)
            throws UserException, ChatException {
        UserApp redUser=userService.getProfile(authentication);
        Chat chats=chatService.addUserToGroup(userId, chatId, redUser);
        return new ResponseEntity<Chat>(chats, HttpStatus.OK);
    }
    @DeleteMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserToGroupHandler(@PathVariable Integer chatId,
                                                         @PathVariable Integer userId,
                                                         Authentication authentication)
            throws UserException, ChatException {
        UserApp redUser=userService.getProfile(authentication);
        Chat chats=chatService.removeFromGroup(userId, chatId, redUser);
        return new ResponseEntity<Chat>(chats, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,
                                                         Authentication authentication)
            throws UserException, ChatException {
        UserApp redUser=userService.getProfile(authentication);
        chatService.deleteChat(chatId, redUser.getUserId());
        ApiResponse res=new ApiResponse("chat delete", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
