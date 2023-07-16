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
}
