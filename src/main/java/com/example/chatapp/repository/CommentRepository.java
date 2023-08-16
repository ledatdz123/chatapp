package com.example.chatapp.repository;

import com.example.chatapp.model.Comment;
import com.example.chatapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}
