package com.example.chatapp.dto;

import lombok.Data;
@Data
public class PopularDTO {
    private Integer userId;
    private String username;
    private String userImage;
    private Long followCount;
    public static PopularDTO convertObjectToArray(Object[] objectArray){
        PopularDTO popularDTO=new PopularDTO();
        popularDTO.setUserId((Integer) objectArray[0]);
        popularDTO.setUsername((String) objectArray[1]);
        popularDTO.setUserImage((String) objectArray[2]);
        popularDTO.setFollowCount((Long) objectArray[3]);
        return popularDTO;
    }
}
