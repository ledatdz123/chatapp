package com.example.chatapp.dto.request;

public class SingleChatRequest {
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public SingleChatRequest(Integer userId) {
        this.userId = userId;
    }

    public SingleChatRequest() {
    }
}
