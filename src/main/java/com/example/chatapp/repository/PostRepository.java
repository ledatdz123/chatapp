package com.example.chatapp.repository;

import com.example.chatapp.model.Post;
import com.example.chatapp.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(UserApp userApp);
    List<Post> findByUserIn(List<UserApp> userApps);
}
