package com.example.chatapp.controller;

import com.example.chatapp.model.UserApp;
import com.example.chatapp.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    UserServiceImpl userService;
    @GetMapping("/")
    public String get(){
        return "User access level";
    }
    @GetMapping("/name")
    @ResponseBody
    public String getName(Authentication authentication) {

        return authentication.getName();
    }
    @GetMapping("/search")
    public ResponseEntity<HashSet<UserApp>> searchUser(@RequestParam("name") String q){
        List<UserApp> users=userServiceImpl.searchUser(q);
        HashSet<UserApp> set=new HashSet<>(users);
        return new ResponseEntity<HashSet<UserApp>>(set, HttpStatus.ACCEPTED);
    }
    @GetMapping("/profile")
    public UserApp getProfile(Authentication authentication){
        UserApp userApp= userServiceImpl.getProfile(authentication);
        return userApp;
    }
    @PostMapping("/savePost/{postId}")
    public ResponseEntity<String> savePostFromUser(Authentication authentication, @PathVariable Integer postId) {
        try {
            UserApp userApp=userService.getProfile(authentication);
            userService.savedPost(userApp.getUserId(), postId);
            // You can return an appropriate success message in the response if needed.
            return ResponseEntity.ok("Post saved successfully.");
        } catch (IllegalArgumentException e) {
            // Return an error response with the appropriate message.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/unSavePost/{postId}")
    public ResponseEntity<String> deleteSavedPost(Authentication authentication, @PathVariable Integer postId) {
        try {
            UserApp userApp=userService.getProfile(authentication);
            userService.deleteSavedPost(userApp.getUserId(), postId);
            // You can return an appropriate success message in the response if needed.
            return ResponseEntity.ok("Saved post deleted successfully.");
        } catch (IllegalArgumentException e) {
            // Return an error response with the appropriate message.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
