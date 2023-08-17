package com.example.chatapp.controller;

import com.example.chatapp.dto.CommentDTO;
import com.example.chatapp.model.Comment;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.services.UserService;
import com.example.chatapp.services.comment.CommentService;
import com.example.chatapp.services.likecomment.LikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeCommentService likeCommentService;
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDTO> createPost(Authentication authentication,
                                                 @PathVariable Integer postId,
                                                 @RequestBody Comment comment) {
        UserApp userApp=userService.getProfile(authentication);
        CommentDTO createComment=commentService.createComment(comment, postId, userApp.getUserId());
        return ResponseEntity.ok(createComment);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Integer id) {
        CommentDTO commentDTO=commentService.findCommentById(id);
        return ResponseEntity.ok(commentDTO);
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentByPostId(@PathVariable Integer id) {
        List<CommentDTO> commentDTO=commentService.findCommentByPost(id);
        return ResponseEntity.ok(commentDTO);
    }
    @PostMapping("/like/{commentId}")
    public ResponseEntity<String> likePostByUserId(Authentication authentication, @PathVariable Integer commentId) {
        UserApp userApp=userService.getProfile(authentication);
        likeCommentService.likePostByUserId(userApp.getUserId(), commentId);
        return ResponseEntity.ok("Comment liked successfully.");
    }
    @DeleteMapping("/unlike/{commentId}")
    public ResponseEntity<String> unLikePostByUserId(Authentication authentication, @PathVariable Integer commentId) {
        try {
            UserApp userApp=userService.getProfile(authentication);
            likeCommentService.unLikePostByUserId(userApp.getUserId(), commentId);
            // You can return an appropriate success message in the response if needed.
            return ResponseEntity.ok("Post deleted successfully.");
        } catch (IllegalArgumentException e) {
            // Return an error response with the appropriate message.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

