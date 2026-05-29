
package com.example.mini.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.mini.controller..*(..))")
    public void beforeController(JoinPoint joinPoint) {

        log.info("Calling method: {}",
                joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "execution(* com.example.mini.service..*(..))",
            returning = "result"
    )
    public void afterReturning(Object result) {

        log.info("Method returned: {}", result);
    }

    @Around("execution(* com.example.mini.controller..*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info("{} executed in {} ms",
                joinPoint.getSignature().getName(),
                (end - start));

        return result;
    }
}

