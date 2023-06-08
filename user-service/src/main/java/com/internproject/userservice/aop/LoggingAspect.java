package com.internproject.userservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.internproject.userservice.service.*.*(..))")
    public void servicePointCut(){}

    @Pointcut("execution(* com.internproject.userservice.service.UserService.loadUserByUsername(..))")
    public void loginPointCut(){}

    @AfterThrowing(pointcut = "servicePointCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LOGGER.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getMessage());
    }

    @Around("servicePointCut() && !loginPointCut()")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        final StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        LOGGER.info("Execution time of {}.{} :: {} ms",
                methodSignature.getDeclaringType().getSimpleName(),
                methodSignature.getName(),
                stopWatch.getTotalTimeMillis());
        return result;
    }
}
