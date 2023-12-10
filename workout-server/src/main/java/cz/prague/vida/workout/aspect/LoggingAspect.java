package cz.prague.vida.workout.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

//    @Pointcut("execution(* cz.prague.vida.workout.service.*.*(..))")
//    public void vidaPackage(){}
//
//    @Before("vidaPackage()")
//    public void beforeVidaPackage(){
//        System.out.println("before calling vida service package");
//    }
}
