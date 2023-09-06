package com.example.chatapp.dto;

import com.example.chatapp.model.Followers;
import com.example.chatapp.model.Post;
import com.example.chatapp.model.Role;
import com.example.chatapp.model.UserApp;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
@Data
public class UserDTO {
    private Integer userId;
    private String username;
    private String name;
    private String password;
    private String userImage;
    private Set<Role> authorities;
    private List<UserDTO> followers;
    private List<UserDTO> following;
    private Set<PostDTO> savedPosts;
    public static UserDTO mapUserToDTO(UserApp userApp){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(userApp.getUserId());
        userDTO.setUsername(userApp.getUsername());
        userDTO.setName(userApp.getName());
        userDTO.setPassword(userApp.getPassword());
        userDTO.setUserImage(userApp.getUserImage());
        Set<String> roles=new HashSet<>();
        userDTO.setAuthorities((Set<Role>) userApp.getAuthorities());

        Set<PostDTO> savedPostDTOs = mapPostsToDTOs(userApp.getSavedPosts());

        userDTO.setSavedPosts(savedPostDTOs);
        userDTO.setFollowers(convertToUserFromDTOList(userApp.getFollowers()));
        userDTO.setFollowing(convertToUserToDTOList(userApp.getFollowing()));
        return userDTO;
    }
    public static Set<PostDTO> mapPostsToDTOs(Set<Post> posts){
        return posts.stream()
                .map(post -> mapUserAndPostToDTO(post)) // Using lambda expression
                .collect(Collectors.toSet());
    }
    public static PostDTO mapUserAndPostToDTO(Post post){
        PostDTO postDTO=new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setImage(post.getImage());
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(post.getUser().getUserId());
        userDTO.setUsername(post.getUser().getUsername());
        postDTO.setUser(userDTO);
        return postDTO;
    }
    private static List<UserDTO> convertToUserFromDTOList(List<Followers> followers) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Followers follower : followers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(follower.getFrom().getUserId());
            userDTO.setUsername(follower.getFrom().getUsername());
            userDTO.setUserImage(follower.getFrom().getUserImage());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
    private static List<UserDTO> convertToUserToDTOList(List<Followers> followers) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Followers follower : followers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(follower.getTo().getUserId());
            userDTO.setUsername(follower.getTo().getUsername());
            userDTO.setUserImage(follower.getTo().getUserImage());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
