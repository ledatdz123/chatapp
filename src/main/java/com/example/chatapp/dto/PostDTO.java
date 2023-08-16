package com.example.chatapp.dto;

import com.example.chatapp.model.Post;
import com.example.chatapp.model.UserApp;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PostDTO {
    private Integer id;
    private String caption;
    private String image;
    private String location;
    private LocalDateTime createAt;
    private String content;
    private List<CommentDTO> comment;
    private List<LikeDTO> likeByUsers;
    private UserDTO user;
    public static UserDTO mapUserToDTO(UserApp userApp){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(userApp.getUserId());
        userDTO.setUsername(userApp.getUsername());
        userDTO.setPassword(userApp.getPassword());
//        Set<String> roles=new HashSet<>();
//        userDTO.setAuthorities((Set<Role>) userApp.getAuthorities());
        return userDTO;
    }
    public static PostDTO mapPostToDTO(Post post){
        PostDTO postDTO=new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setImage(post.getImage());
        postDTO.setLocation(post.getLocation());
        postDTO.setCreateAt(post.getCreateAt());
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(post.getUser().getUserId());
        userDTO.setUsername(post.getUser().getUsername());
        userDTO.setUserImage(post.getUser().getUserImage());
        postDTO.setUser(userDTO);
        return postDTO;
    }
}
