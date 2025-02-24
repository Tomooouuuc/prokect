package com.example.picares.model.dto.user;

import com.example.picares.common.PageRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends PageRequest {
    private String userAccount;
    private String userName;
    private String userProfile;
    private String userRole;
}
