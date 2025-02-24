package com.example.picares.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopTest {

    // 前置通知，在目标方法执行之前执行
    @Before("execution(* com.example.picares.test.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before method: " + joinPoint.getSignature().getName());
    }

    // 后置通知，在目标方法执行之后执行
    @After("execution(* com.example.picares.test.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("After method: " + joinPoint.getSignature().getName());
    }

    @Around("execution(* com.example.picares.test.*.*(..))")
    public void afterAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before Around: ");
        joinPoint.proceed();
        System.out.println("After Around: ");
    }
}
