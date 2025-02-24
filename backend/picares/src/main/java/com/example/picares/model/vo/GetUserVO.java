package com.example.picares.model.vo;

import java.util.Date;

import lombok.Data;

@Data
public class GetUserVO {
    private Long id;
    private String userAccount;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
    private Date createTime;
}
