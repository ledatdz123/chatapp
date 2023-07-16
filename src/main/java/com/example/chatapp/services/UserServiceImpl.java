package com.example.chatapp.services;

import com.example.chatapp.dto.request.UpdateUserRequest;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepo;
    public UserApp getProfile(Authentication authentication){
        Optional<UserApp> userApp=userRepo.findByUsername(authentication.getName());
        if (userApp.isPresent()){
            return userApp.get();
        }
        throw new UsernameNotFoundException("not found");
    }

    @Override
    public UserApp finUserById(Integer id) throws UserException {
        Optional<UserApp> opt=userRepo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User not found with id"+id);
    }

    @Override
    public UserApp finUserProfile(String jwt) throws UserException {
        return null;
    }

    @Override
    public UserApp updateUser(Integer id, UpdateUserRequest red) throws UserException {
        return null;
    }

    @Override
    public List<UserApp> searchUser(String query) {
        List<UserApp> userApps=userRepo.searchUser(query);
        return userApps;
    }
}
