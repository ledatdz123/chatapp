package com.example.chatapp.repository;

import com.example.chatapp.model.Comment;
import com.example.chatapp.model.LikeComment;
import com.example.chatapp.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Integer> {
    boolean existsByCommentAndUser(Comment comment, UserApp userApp);
    List<LikeComment> findByComment(Comment comment);
    LikeComment findByCommentAndUser(Comment comment, UserApp userApp);
}
