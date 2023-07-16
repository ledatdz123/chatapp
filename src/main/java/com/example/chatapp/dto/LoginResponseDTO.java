package com.example.chatapp.dto;

import com.example.chatapp.model.UserApp;

public class LoginResponseDTO {
    private UserApp user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(UserApp user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

    public UserApp getUser(){
        return this.user;
    }

    public void setUser(UserApp user){
        this.user = user;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}
