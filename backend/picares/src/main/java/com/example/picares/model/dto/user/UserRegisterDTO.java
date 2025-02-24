package com.example.picares.model.dto.user;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
