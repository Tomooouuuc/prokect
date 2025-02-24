package com.example.picares.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.picares.annotation.AuthCheck;
import com.example.picares.constant.UserConstant;
import com.example.picares.exception.BusinessException;
import com.example.picares.exception.ErrorCode;
import com.example.picares.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Before("@annotation(authCheck)")
    public void doInterceptor(AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String userRole = userService.getLoginUser(request).getUserRole();

        if (UserConstant.ADMIN_ROLE.equals(mustRole) && !UserConstant.ADMIN_ROLE.equals(userRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
    }
}
