package com.andrewsavich.djguesser.beatportscraper.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ScraperLogAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.andrewsavich.djguesser.beatportscraper..*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info("Entering method " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.andrewsavich.djguesser.beatportscraper..*.*(..))", returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("{} returned with value: {}", joinPoint.getSignature(), result);
    }
}
