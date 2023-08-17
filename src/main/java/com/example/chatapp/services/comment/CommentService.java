package com.example.chatapp.services.comment;

import com.example.chatapp.dto.CommentDTO;
import com.example.chatapp.model.Comment;

import java.util.List;

public interface CommentService {
    public CommentDTO createComment(Comment comment, Integer postId, Integer userId);
    public void deleteComment(Integer comId, Integer postId);
    public CommentDTO findCommentById(Integer comId);
    public List<CommentDTO> findCommentByPost(Integer postId);
}

