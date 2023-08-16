package com.example.chatapp.repository;

import com.example.chatapp.model.LikePost;
import com.example.chatapp.model.Post;
import com.example.chatapp.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikePostRepository extends JpaRepository<LikePost, Integer> {
    boolean existsByUserAndPost(UserApp user, Post post);
    List<LikePost> findByPost(Post post);
    LikePost findByPostAndUser(Post post, UserApp userApp);
}
