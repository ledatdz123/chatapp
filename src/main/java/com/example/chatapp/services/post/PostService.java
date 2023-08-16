package com.example.chatapp.services.post;

import com.example.chatapp.dto.PostDTO;
import com.example.chatapp.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post, Integer userId);
    public List<Post> getAllPosts();
    public PostDTO findPostById(Integer postId);
    public List<PostDTO> findPostByUserId(Integer userId);
    public List<PostDTO> findPostByUserFollow(List<Integer> userIds);
    public void deletePost(Integer userId, Integer postId);
}
