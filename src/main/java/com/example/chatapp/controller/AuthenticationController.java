package com.example.chatapp.controller;

import com.example.chatapp.dto.LoginResponseDTO;
import com.example.chatapp.dto.RegistrationDTO;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.security.services.AuthenticationService;
import com.example.chatapp.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @PostMapping("/register")
    public UserApp registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
    @GetMapping("/profile")
    public UserApp getProfile(Authentication authentication){
        UserApp userApp= userServiceImpl.getProfile(authentication);
        return userApp;
    }
}
