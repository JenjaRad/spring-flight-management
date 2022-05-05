package com.eugene.flight.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Log4j2
public class ControllerLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)" +
            " || within(@org.springframework.stereotype.Service *)")
    public void isControllerOrServicePointcut() {
    }

    @Pointcut("within(com.eugene.flight.service..*)" +
            " || within(com.eugene.flight.controller..*)" +
            " || within(com.eugene.flight..*)")
    public void appPackagePointcut() {
    }

    @AfterThrowing(pointcut = "isControllerOrServicePointcut() && appPackagePointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature()
                        .getDeclaringTypeName(),
                joinPoint.getSignature()
                        .getName(), ex.getCause() != null ? ex.getCause() : "null");
    }

    @Around("isControllerOrServicePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            String className = joinPoint.getSignature()
                    .getDeclaringTypeName();
            String methodName = joinPoint.getSignature()
                    .getName();
            Object result = joinPoint.proceed();
            long elapsedTime = System.nanoTime() - start;
            log.debug("Method " + className + "." + methodName + " ()" + " execution time : "
                    + elapsedTime + " ms");

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature()
                    .getName() + "()");
            throw e;
        }
    }
}
