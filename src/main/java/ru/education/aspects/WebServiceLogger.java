package ru.education.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {

    private static Logger LOG = LoggerFactory.getLogger(WebServiceLogger.class);

    @Pointcut(value = "execution(public * ru.education.service.ProductService.*(..))")
    public void serviceMethod() {};

    @Pointcut("@annotation(ru.education.annotation.Loggable)")
    public void loggableMethods() {};

    /*@Around(value = "serviceMethod() && loggableMethods()")
    public Object logWebServiceCall (ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Call method " + methodName + " with args " + Arrays.toString(methodArgs));

        Object result = thisJoinPoint.proceed();

        LOG.info("Method " + methodName + " returns " + result);

        return result;
    }*/

    @Before(value = "serviceMethod()")
    public void logWebServiceCallBefore(JoinPoint thisJoinPoint) {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Call method " + methodName + " with args " + Arrays.toString(methodArgs));
    }

    @AfterReturning(value = "serviceMethod()")
    public void logWebServiceCallAfter(JoinPoint thisJoinPoint) {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Method " + methodName + " completed successfully");
    }

    @AfterThrowing(value = "serviceMethod()")
    public void logWebServiceCallException(JoinPoint thisJoinPoint) {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Method " + methodName + " failed");
    }
}
