package com.example.chatapp.controller;

import com.example.chatapp.exception.ResourceNotFoundException;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.MessageReact;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.MessageReactRepository;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/react")
public class MessageReactController {
    @Autowired
    private MessageReactRepository messageReactRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;
    @GetMapping("/message/{messageId}/reacts")
    public ResponseEntity<List<MessageReact>> getAllCommentsByTutorialId(@PathVariable(value = "messageId") Integer messageId) throws ResourceNotFoundException {
        if (!messageRepository.existsById(messageId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + messageId);
        }
        List<MessageReact> messageReacts= messageReactRepository.findByMessage_Id(messageId);
        return new ResponseEntity<>(messageReacts, HttpStatus.OK);
    }
    @PostMapping("/message/{messageId}/reacts")
    public ResponseEntity<MessageReact> createComment(@PathVariable(value = "messageId") Integer messageId,
                                                 @RequestBody MessageReact reactRequest,
                                                      Authentication authentication) throws ResourceNotFoundException {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + messageId));
        UserApp userApp=userService.getProfile(authentication);
        MessageReact messageReact=new MessageReact();
        messageReact.setContent(reactRequest.getContent());
        messageReact.setMessage(message);
        messageReact.setUserReact(userApp);
        messageReact.setTimestamp(LocalDateTime.now());
        messageReactRepository.save(messageReact);
        return new ResponseEntity<>(messageReact, HttpStatus.CREATED);
    }
}
