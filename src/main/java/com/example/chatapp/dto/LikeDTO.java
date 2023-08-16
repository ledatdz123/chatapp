package com.example.chatapp.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LikeDTO {
    private Integer id;
    private UserDTO user;
    private LocalDateTime createAt;
}
