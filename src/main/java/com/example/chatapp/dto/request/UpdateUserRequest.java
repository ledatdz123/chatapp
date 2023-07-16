package com.example.chatapp.dto.request;

public class UpdateUserRequest {
    private String fullName;
    private String profile_picture;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public UpdateUserRequest(String fullName, String profile_picture) {
        this.fullName = fullName;
        this.profile_picture = profile_picture;
    }

    public UpdateUserRequest() {
    }
}
