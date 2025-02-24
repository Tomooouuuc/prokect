package com.example.picares.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String userAccount;
    private String userPassword;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
    private Date editTime;
    private Date createTime;
    private Date updateTime;
    private Integer isDelete;
}
