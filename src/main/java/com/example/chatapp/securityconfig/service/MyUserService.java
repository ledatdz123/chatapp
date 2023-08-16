package com.example.chatapp.securityconfig.service;

import com.example.chatapp.dto.request.UserDTO;
import com.example.chatapp.dto.response.BaseResponseDTO;

public interface MyUserService {
    BaseResponseDTO registerAccount(UserDTO userDTO);
}

