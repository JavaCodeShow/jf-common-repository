package com.jf.common.redis.annotation;

import com.jf.common.redis.service.lock.DistributeLockManager;
import com.jf.common.utils.exception.BizException;
import com.jf.common.utils.meta.enums.GlobalErrorCodeEnum;
import com.jf.common.utils.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 */
@Aspect
@Slf4j
@Component
public class LockMethodInterceptor {

    @Autowired
    private DistributeLockManager distributeLockManager;

    /**
     * 防止表单重复提交
     */
    @Around("@annotation(com.jf.common.redis.annotation.ReSubmitLock)")
    public Object reSubmitLockInterceptor(ProceedingJoinPoint pjp)
            throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        ReSubmitLock lockAnnotation = method.getAnnotation(ReSubmitLock.class);

        String lockKey = generateLockKey(pjp);

        final boolean success = distributeLockManager.tryLock(lockKey,
                lockAnnotation.waitTime(), lockAnnotation.leaseTime(),
                TimeUnit.SECONDS);
        log.info("线程 = [{}] 获取锁成功, lockKey = [{}], waitTime = [{}], leaseTime = [{}]",
                Thread.currentThread().getName(), lockKey,
                lockAnnotation.waitTime(), lockAnnotation.leaseTime());
        if (!success) {
            // 重复提交异常不删除key
            throw new BizException(GlobalErrorCodeEnum.RESUBMIT);
        }

        return pjp.proceed();
    }

    /**
     * 生成防重复提交锁的key的名字
     */
    private String generateLockKey(ProceedingJoinPoint pjp) {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        ReSubmitLock lockAnnotation = method.getAnnotation(ReSubmitLock.class);

        // TODO 同一个人同一个方法(userId 和userType需要取系统具体的值)
        return "userId" + lockAnnotation.delimiter() + "userType"
                + lockAnnotation.delimiter() + method.getName();
    }

    /**
     * 防止方法同时被多个线程或者客户端执行
     */
    @Around("@annotation(com.jf.common.redis.annotation.DistributeLock)")
    public Object distributeLockInterceptor(ProceedingJoinPoint pjp)
            throws Throwable {

        // 获取该注解的实例对象
        DistributeLock lockAnnotaion = ((MethodSignature) pjp.getSignature())
                .getMethod().getAnnotation(DistributeLock.class);

        String lockKey = lockAnnotaion.lockKey();

        if (StringUtils.isEmpty(lockKey)) {
            throw new BizException("分布式锁必须指定key的值");
        }

        if (distributeLockManager.tryLock(lockKey, lockAnnotaion.waitTime(),
                lockAnnotaion.leaseTime(), TimeUnit.SECONDS)) {
            try {
                return pjp.proceed();
            } finally {
                if (distributeLockManager.isLocked(lockKey)) {
                    if (distributeLockManager.isHeldByCurrentThread(lockKey)) {
                        distributeLockManager.unlock(lockKey);
                    }
                }
            }
        }
        return BaseResult.fail(GlobalErrorCodeEnum.NOT_GET_LOCK);
    }

}
