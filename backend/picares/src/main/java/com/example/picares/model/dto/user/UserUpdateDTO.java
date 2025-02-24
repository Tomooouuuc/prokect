package com.example.picares.model.dto.user;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long id;
    private String userAccount;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
}
