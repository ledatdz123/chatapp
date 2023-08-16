package com.example.chatapp.controller;

import com.example.chatapp.dto.PostDTO;
import com.example.chatapp.model.Post;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.services.UserService;
import com.example.chatapp.services.likepost.LikePostService;
import com.example.chatapp.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikePostService likePostService;
//    @PostMapping("/{userId}")
//    public PostDTO createPost(@PathVariable Integer userId, @RequestBody Post post) {
//        Post createPost=postService.createPost(post, userId);
//        PostDTO postDTO=PostDTO.mapPostToDTO(createPost);
//        return postDTO;
//    }

    @PostMapping("/create")
    public PostDTO createPost(@RequestBody Post post, Authentication authentication) {
        UserApp userApp=userService.getProfile(authentication);
        Post createPost=postService.createPost(post, userApp.getUserId());
        PostDTO postDTO=PostDTO.mapPostToDTO(createPost);
        return postDTO;
    }
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Integer userId) {
        List<PostDTO> posts = postService.findPostByUserId(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostsById(@PathVariable Integer postId) {
        PostDTO postDTO=postService.findPostById(postId);
        return ResponseEntity.ok(postDTO);
    }
    @GetMapping("/follow/{ids}")
    public ResponseEntity<List<PostDTO>> getPostsByUserIds(@PathVariable List<Integer> ids) {
        List<PostDTO> list=postService.findPostByUserFollow(ids);
        return ResponseEntity.ok(list);
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostByUserIdAndPostId(Authentication authentication, @PathVariable Integer postId) {
        try {
            UserApp userApp=userService.getProfile(authentication);
            postService.deletePost(userApp.getUserId(),postId);
            // You can return an appropriate success message in the response if needed.
            return ResponseEntity.ok("Post deleted successfully.");
        } catch (IllegalArgumentException e) {
            // Return an error response with the appropriate message.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @PostMapping("/{userId}/like/{postId}")
//    public ResponseEntity<String> likePostByUserId(@PathVariable Integer userId, @PathVariable Integer postId) {
//        likePostService.likePostByUserId(userId, postId);
//        return ResponseEntity.ok("Post liked successfully.");
//    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<String> likePostByUserId(Authentication authentication, @PathVariable Integer postId) {
        UserApp userApp=userService.getProfile(authentication);
        likePostService.likePostByUserId(userApp.getUserId(), postId);
        return ResponseEntity.ok("Post liked successfully.");
    }
    @DeleteMapping("/unlike/{postId}")
    public ResponseEntity<String> unLikePostByUserId(Authentication authentication, @PathVariable Integer postId) {
        try {
            UserApp userApp=userService.getProfile(authentication);
            likePostService.unLikePostByUserId(userApp.getUserId(), postId);
            // You can return an appropriate success message in the response if needed.
            return ResponseEntity.ok("Post deleted successfully.");
        } catch (IllegalArgumentException e) {
            // Return an error response with the appropriate message.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
