package na.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {
    Logger logger = LoggerFactory.getLogger(LogAdvice.class);

    @Around("execution(* na.spring.service.SampleService*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Target : " + pjp.getTarget());
        logger.info("Param : " + Arrays.toString(pjp.getArgs()));
        logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        logger.info("TIME : " + (end - start));
        return result;
    }

    @Before("execution(* na.spring.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
    public void logBeforeWithParam(String str1, String str2) {
        logger.info("str1 : " + str1);
        logger.info("str2 : " + str2);
    }

    @AfterThrowing(pointcut = "execution(* na.spring.service.SampleService*.*(..))", throwing = "exception")
    public void logException(Exception exception) {
        logger.info("Exception....!!!!");
        logger.info("exception : " + exception);
    }

}
