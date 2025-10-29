package com.gdp.backend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class UserAccessHandler {

    @Autowired
    @Qualifier("logger")
    Logger logger;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
        // Do nothing
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {
        // Do nothing
    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
        // Do nothing
    }

    @Around("controller() && allMethod()")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {

        return commonAround(joinPoint);
    }


    @AfterThrowing(pointcut = "controller() && allMethod()", throwing = "exception")
    public void logAfterThrowingController(JoinPoint joinPoint, Throwable exception) {
        commonLogAfterThrow(joinPoint, exception);
    }

    @Around("service() && allMethod()")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {

        return commonAround(joinPoint);
    }

    @AfterThrowing(pointcut = "service() && allMethod()", throwing = "exception")
    public void logAfterThrowingService(JoinPoint joinPoint, Throwable exception) {
        commonLogAfterThrow(joinPoint, exception);
    }

    private Object commonAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            logger.info(String.format("Method Started for %s.%s ()", className, methodName));
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            logger.info(String.format("Method ended for %s.%s () With execution time : %d ms", className, methodName, elapsedTime));

            return result;
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Illegal argument %s in %s()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName()));
            throw e;
        }
    }

    public void commonLogAfterThrow(JoinPoint joinPoint, Throwable exception) {
        logger.error(String.format("An exception has been thrown in %s %s ()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()));
        logger.error(String.format("Cause : %s", exception.getMessage()));
    }
}

