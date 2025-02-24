package com.example.picares.service;

import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.entity.User;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    void userRegister(String userAccount, String userPassword, String checkPassword);

    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    LoginUserVO getLoginUser(HttpServletRequest request);

    void userLogout(HttpServletRequest request);

    void updateUser(User user);

    void deleteUser(Long id);

    UserVO selectUser(UserQueryDTO userQueryDTO);
}
