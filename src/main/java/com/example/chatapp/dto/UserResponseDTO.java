package com.example.chatapp.dto;

public class UserResponseDTO {
    private UserDTO user;
    private String jwt;

    public UserResponseDTO(UserDTO user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public UserResponseDTO() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
