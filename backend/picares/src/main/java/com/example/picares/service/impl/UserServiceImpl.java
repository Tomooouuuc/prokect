package com.example.picares.service.impl;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.picares.constant.UserConstant;
import com.example.picares.exception.BusinessException;
import com.example.picares.exception.ErrorCode;
import com.example.picares.exception.ThrowUtil;
import com.example.picares.mapper.UserMapper;
import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.entity.User;
import com.example.picares.model.vo.GetUserVO;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.UserVO;
import com.example.picares.service.UserService;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void userRegister(String userAccount, String userPassword, String checkPassword) {
        ThrowUtil.throwIf(StrUtil.hasBlank(userAccount, userPassword, checkPassword), ErrorCode.PARAMS_ERROR);

        ThrowUtil.throwIf(userAccount.length() < 4 || userAccount.length() > 32, ErrorCode.PARAMS_ERROR, "账号长度不符合规则");

        String pattern = "^[a-zA-Z0-9]+$";
        ThrowUtil.throwIf(!Pattern.matches(pattern, userAccount), ErrorCode.PARAMS_ERROR, "账号包含非法字符");

        ThrowUtil.throwIf(userPassword.length() < 6 || checkPassword.length() < 6, ErrorCode.PARAMS_ERROR, "密码过短");

        ThrowUtil.throwIf(userPassword.length() > 16 || checkPassword.length() > 16, ErrorCode.PARAMS_ERROR, "密码过长");

        ThrowUtil.throwIf(!userPassword.equals(checkPassword), ErrorCode.PARAMS_ERROR, "密码和确认密码不一样");

        String encodePassword = getEncodePassword(userPassword);
        String userName = "游客" + (new Random().nextInt(900000) + 100000);

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encodePassword);
        user.setUserName(userName);
        try {
            userMapper.register(user);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        ThrowUtil.throwIf(StrUtil.hasBlank(userAccount, userPassword), ErrorCode.PARAMS_ERROR);
        ThrowUtil.throwIf(userAccount.length() < 4 || userAccount.length() > 32, ErrorCode.PARAMS_ERROR);
        String pattern = "^[a-zA-Z0-9]+$";
        ThrowUtil.throwIf(!Pattern.matches(pattern, userAccount), ErrorCode.PARAMS_ERROR);
        ThrowUtil.throwIf(userPassword.length() < 6 || userPassword.length() > 16, ErrorCode.PARAMS_ERROR);
        String encodePassword = getEncodePassword(userPassword);
        User user;
        try {
            user = userMapper.login(userAccount, encodePassword);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        ThrowUtil.throwIf(user == null, ErrorCode.PARAMS_ERROR, "账号不存在或密码错误");
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);

        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, loginUserVO);
        return loginUserVO;
    }

    private String getEncodePassword(String password) {
        final String SALT = "picares";
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes());
    }

    @Override
    public LoginUserVO getLoginUser(HttpServletRequest request) {
        LoginUserVO loginUser = (LoginUserVO) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        ThrowUtil.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);

        loginUser = userMapper.selectById(loginUser.getId());
        ThrowUtil.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);

        return loginUser;
    }

    @Override
    public void userLogout(HttpServletRequest request) {
        LoginUserVO loginUser = (LoginUserVO) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        ThrowUtil.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);

        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
    }

    @Override
    public void updateUser(User user) {
        ThrowUtil.throwIf(user.getUserAccount().length() < 4 || user.getUserAccount().length() > 32,
                ErrorCode.PARAMS_ERROR, "账号长度不符合规则");
        String pattern = "^[a-zA-Z0-9]+$";
        ThrowUtil.throwIf(!Pattern.matches(pattern, user.getUserAccount()), ErrorCode.PARAMS_ERROR, "账号包含非法字符");
        ThrowUtil.throwIf(user.getUserName().length() > 32, ErrorCode.PARAMS_ERROR, "用户名称过长");
        ThrowUtil.throwIf(user.getUserAvatar().length() > 1024, ErrorCode.PARAMS_ERROR);
        ThrowUtil.throwIf(user.getUserProfile().length() > 128, ErrorCode.PARAMS_ERROR, "用户简介过长");

        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            userMapper.deleteUser(id);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public UserVO selectUser(UserQueryDTO userQueryDTO) {
        UserVO userVO = new UserVO();
        int current = userQueryDTO.getCurrent();
        int pageSize = userQueryDTO.getPageSize();
        userQueryDTO.setCurrent(pageSize * (current - 1));
        try {
            System.out.println("查询");
            List<GetUserVO> getUserVO = userMapper.selectUser(userQueryDTO);
            int total = userMapper.selectUserCount(userQueryDTO);
            System.out.println("getUserVO：" + getUserVO);
            System.out.println("total：" + total);
            userVO.setRecords(getUserVO);
            userVO.setTotal(total);
            System.out.println("结束");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        System.out.println("userVO：" + userVO);
        return userVO;
    }
}
