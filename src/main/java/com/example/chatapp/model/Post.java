package com.example.chatapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String caption;
    private String image;
    private String location;
    private LocalDateTime createAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserApp user;

}

