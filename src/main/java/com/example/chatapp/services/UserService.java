package com.example.chatapp.services;

import com.example.chatapp.dto.request.UpdateUserRequest;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.UserApp;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    public UserApp getProfile(Authentication authentication);
    public UserApp finUserById(Integer id) throws UserException;
    public UserApp finUserProfile(String jwt) throws UserException;
    public UserApp updateUser(Integer id, UpdateUserRequest red) throws UserException;
    public List<UserApp> searchUser(String query);
    public UserApp updateUser(UserApp updateUser, UserApp existsUser) throws UserException;
    public List<UserApp> findUserByIds(List<Integer> userIds) throws UserException;
    public List<UserApp> searchUserByQuery(String query);
    public void savedPost(Integer userId, Integer postId);
    public void deleteSavedPost(Integer userId, Integer postId);
}
