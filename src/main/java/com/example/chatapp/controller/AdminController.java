package com.example.chatapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    public class UserController {
        @GetMapping("/")
        public String get(){
            return "Admin access level";
        }
    }
}
