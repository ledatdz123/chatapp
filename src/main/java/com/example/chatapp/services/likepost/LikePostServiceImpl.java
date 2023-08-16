package com.example.chatapp.services.likepost;

import com.example.chatapp.model.LikePost;
import com.example.chatapp.model.Post;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.LikePostRepository;
import com.example.chatapp.repository.PostRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikePostServiceImpl implements LikePostService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikePostRepository likeRepository;

    @Override
    public void likePostByUserId(Integer userId, Integer postId) {
        UserApp user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        if (user != null && post != null) {
            // Check if the user has already liked the post
            if (!hasLikedPost(user, post)) {
                LikePost like = new LikePost();
                like.setUser(user);
                like.setPost(post);
                like.setCreateAt(LocalDateTime.now());
                likeRepository.save(like);
            }
        }
    }

    @Override
    public void unLikePostByUserId(Integer userId, Integer postId) {
        UserApp currentUser = userRepository.findById(userId).orElse(null);

        if (currentUser == null) {
            // Handle error: User not found with the given ID.
            return;
        }
        Post post=postRepository.findById(postId).orElse(null);
        LikePost likePost=likeRepository.findByPostAndUser(post, currentUser);
        likeRepository.delete(likePost);
    }

    private boolean hasLikedPost(UserApp user, Post post) {
        // Check if the user has already liked the post
        return likeRepository.existsByUserAndPost(user, post);
    }
}

