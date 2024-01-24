package dev.practice.QRCodeGenerator.config.aspect.log;

import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogExecutionTime;
import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController;
import dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogProceed;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Around("@annotation(dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        Instant startTime = Instant.now();
        Object result = proceedingJoinPoint.proceed();
        String addtionalMessage = methodSignature.getMethod().getAnnotation(LogExecutionTime.class).additionalMessage();
        long elapsedTime = Duration.between(startTime, Instant.now()).toMillis();

        log.info("Class Name : {}, Method Name : {}, Message : {}, Elapsed Time: {}ms",
                className, methodName, addtionalMessage, elapsedTime);
        log.info("Result: {}", result);
        return result;
    }

    @Around("@annotation(dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogProceed)")
    public Object logProceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        String message = methodSignature.getMethod().getAnnotation(LogProceed.class).Message();

        Object result = proceedingJoinPoint.proceed();

        if (message.isEmpty())
            log.info("{} : {} results : {}", className, methodName, result);
        else
            log.info("{} : {} with {} results : {}", className, methodName, message, result);

        return result;
    }

    @Around("@annotation(dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForController)")
    public Object logForController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        RequestMethod requestMethod = methodSignature.getMethod().getAnnotation(LogForController.class).Request();

        log.info("{} : {} Request of {}", className, requestMethod, methodName);

        Object result = proceedingJoinPoint.proceed();

        log.info("{}() Result : {}", className+" : "+methodName, result);

        return result;
    }

    @Around("@annotation(dev.practice.QRCodeGenerator.config.aspect.log.annotation.LogForUtils)")
    public Object logForUtils(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();

        log.info("{} Enter {}", className, methodName);

        Object result = proceedingJoinPoint.proceed();

        log.info("{} : {} Result : {}", className, methodName, result);

        return result;
    }

}