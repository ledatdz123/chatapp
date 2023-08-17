package com.example.chatapp.services.likecomment;

import com.example.chatapp.model.Comment;
import com.example.chatapp.model.LikeComment;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.CommentRepository;
import com.example.chatapp.repository.LikeCommentRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeCommentServiceImpl implements LikeCommentService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeCommentRepository likeCommentRepository;
    @Override
    public void likePostByUserId(Integer userId, Integer postId) {
        UserApp user = userRepository.findById(userId).orElse(null);
        Comment comment = commentRepository.findById(postId).orElse(null);

        if (user != null && comment != null) {
            // Check if the user has already liked the post
            if (!hasLikedComment(user, comment)) {
                LikeComment like = new LikeComment();
                like.setUser(user);
                like.setComment(comment);
                like.setCreateAt(LocalDateTime.now());
                likeCommentRepository.save(like);
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
        Comment comment=commentRepository.findById(postId).orElse(null);
        LikeComment likePost=likeCommentRepository.findByCommentAndUser(comment, currentUser);
        likeCommentRepository.delete(likePost);
    }
    private boolean hasLikedComment(UserApp user, Comment comment) {
        // Check if the user has already liked the post
        return likeCommentRepository.existsByCommentAndUser(comment, user);
    }
}

