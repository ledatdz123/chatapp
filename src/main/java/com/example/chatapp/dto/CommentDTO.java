package com.example.chatapp.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
    private Integer id;
    private String comment;
    private UserDTO user;
    private LocalDateTime createAt;
    private List<LikeDTO> likeByUsers;
}
