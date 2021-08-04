package com.jf.common.redis.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author luxinghui
 * @date 2019-05-23
 */
@Component
public class LockKeyGenerator implements CacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        ReSubmitLock lockAnnotation = method.getAnnotation(ReSubmitLock.class);

        // TODO 同一个人同一个方法(userId 和userType需要取系统具体的值)
        return "userId" + lockAnnotation.delimiter() + "userType"
                + lockAnnotation.delimiter() + method.getName();
    }
}
