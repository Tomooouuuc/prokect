package com.example.picares.exception;

public class ThrowUtil {
    public static void throwIf(boolean condition,RuntimeException runtimeException){
        if(condition){
            throw runtimeException;
        }
    }

    public static void throwIf(boolean condition,ErrorCode errorCode){
        throwIf(condition, new BusinessException(errorCode));
    }

    public static void throwIf(boolean condition,ErrorCode errorCode,String message){
        throwIf(condition, new BusinessException(errorCode,message));
    }
}
