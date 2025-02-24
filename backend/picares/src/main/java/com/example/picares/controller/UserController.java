package com.example.picares.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.picares.annotation.AuthCheck;
import com.example.picares.common.BaseResponse;
import com.example.picares.common.DeleteRequest;
import com.example.picares.common.ResultUtil;
import com.example.picares.constant.UserConstant;
import com.example.picares.exception.ErrorCode;
import com.example.picares.exception.ThrowUtil;
import com.example.picares.model.dto.user.UserLoginDTO;
import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.dto.user.UserRegisterDTO;
import com.example.picares.model.dto.user.UserUpdateDTO;
import com.example.picares.model.entity.User;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.UserVO;
import com.example.picares.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Boolean> userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        ThrowUtil.throwIf(userRegisterDTO == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterDTO.getUserAccount();
        String userPassword = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtil.success(true);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        ThrowUtil.throwIf(userLoginDTO == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginDTO.getUserAccount();
        String userPassword = userLoginDTO.getUserPassword();

        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);

        return ResultUtil.success(loginUserVO);
    }

    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        LoginUserVO loginUser = userService.getLoginUser(request);
        return ResultUtil.success(loginUser);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        userService.userLogout(request);
        return ResultUtil.success(true);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        ThrowUtil.throwIf(userUpdateDTO == null || userUpdateDTO.getId() == null, ErrorCode.PARAMS_ERROR);
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);
        userService.updateUser(user);
        return ResultUtil.success(true);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {

        ThrowUtil.throwIf(deleteRequest == null || deleteRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        userService.deleteUser(deleteRequest.getId());
        return ResultUtil.success(true);
    }

    @PostMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserVO> selectUser(@RequestBody UserQueryDTO userQueryDTO) {
        System.out.println("查询请求");
        ThrowUtil.throwIf(userQueryDTO == null, ErrorCode.PARAMS_ERROR);
        UserVO userVO = userService.selectUser(userQueryDTO);
        return ResultUtil.success(userVO);
    }
}
