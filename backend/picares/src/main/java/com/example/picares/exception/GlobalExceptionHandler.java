package com.example.picares.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.picares.common.BaseResponse;
import com.example.picares.common.ResultUtil;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e){
        log.error("businessException:",e);
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException:",e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR,"系统错误");
    }
}
