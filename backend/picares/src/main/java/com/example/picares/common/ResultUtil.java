package com.example.picares.common;

import com.example.picares.exception.ErrorCode;

public class ResultUtil {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    public static BaseResponse<?> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse<?> error(int code,String message){
        return new BaseResponse<>(code, null, message);
    }

    public static BaseResponse<?> error(ErrorCode errorCode,String message){
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }
}
