package cz.prague.vida.workout.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* cz.prague.vida.workout.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* cz.prague.vida.workout.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("execution(* cz.prague.vida.workout.dao.*.*(..))")
    private void forDaoPackage() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint) {
        String theMethod = theJoinPoint.getSignature().toShortString();
        logger.info("calling method: " + theMethod);
        Object[] args = theJoinPoint.getArgs();
        for (Object tempArg : args) {
            logger.info("argument: " + tempArg);
        }
    }

    // add @AfterReturning advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
        String theMethod = theJoinPoint.getSignature().toShortString();
        logger.info("from method: " + theMethod);
        logger.info("result: " + theResult);
    }
}











