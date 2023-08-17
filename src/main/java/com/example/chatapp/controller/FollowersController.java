package com.example.chatapp.controller;

import com.example.chatapp.model.Followers;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.services.follow.FollowersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/userfollow")
public class FollowersController {

    private final FollowersService followersService;

    @Autowired
    public FollowersController(FollowersService followersService) {
        this.followersService = followersService;
    }
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/{fromUserId}/follow/{toUserId}")
    public Followers createFollower(@PathVariable Integer fromUserId, @PathVariable Integer toUserId) {
        UserApp fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new EntityNotFoundException("From user not found"));
        UserApp toUser = userRepository.findById(toUserId).orElseThrow(() -> new EntityNotFoundException("To user not found"));
        return followersService.createFollower(fromUser, toUser);
    }

    @GetMapping
    public List<Followers> getAllFollowers() {
        return followersService.getAllFollowers();
    }

    // Add more controller methods as needed for followers-related API endpoints
    @PostMapping("/{fromUserId}/unfollow/{toUserId}")
    public ResponseEntity<String> unfollowUser(
            @PathVariable("fromUserId") Integer fromUserId,
            @PathVariable("toUserId") Integer toUserId) {

        UserApp fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new EntityNotFoundException("From user not found"));
        UserApp toUser = userRepository.findById(toUserId).orElseThrow(() -> new EntityNotFoundException("To user not found"));

        if (fromUser == null || toUser == null) {
            return ResponseEntity.notFound().build();
        }

        followersService.unfollowUser(fromUser, toUser);
        return ResponseEntity.ok("Unfollowed successfully.");
    }
}

