package com.example.chatapp.services.likepost;

public interface LikePostService {
    public void likePostByUserId(Integer userId, Integer postId);
    public void unLikePostByUserId(Integer userId, Integer postId);
}
