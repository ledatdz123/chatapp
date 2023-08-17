package com.example.chatapp.services.likecomment;

public interface LikeCommentService {
    public void likePostByUserId(Integer userId, Integer postId);
    public void unLikePostByUserId(Integer userId, Integer postId);
}
