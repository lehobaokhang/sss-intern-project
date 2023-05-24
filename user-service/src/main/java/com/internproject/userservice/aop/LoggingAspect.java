package com.internproject.userservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.internproject.userservice.controller.*.*(..))")
    public void controllerPointCut(){}

    @Before("controllerPointCut()")
    public void logBeforeController(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info(String.format("Executing method %s.%s", className, methodName));
    }
}
