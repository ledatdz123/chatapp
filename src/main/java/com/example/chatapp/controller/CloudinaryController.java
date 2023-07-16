package com.example.chatapp.controller;

import com.example.chatapp.dto.response.ResponseMessageDTO;
import com.example.chatapp.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class CloudinaryController {
    @Autowired
    private CloudinaryService cloudDinaryService;
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessageDTO> upLoad(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        Map<?, ?> resultMap = cloudDinaryService.upload(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageDTO(
                HttpStatus.OK, resultMap.get("url").toString(), LocalDateTime.now()));
    }
}
