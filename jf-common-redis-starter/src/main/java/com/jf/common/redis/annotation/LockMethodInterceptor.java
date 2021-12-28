package com.jf.common.redis.annotation;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jf.common.redis.service.lock.DistributeLockService;
import com.jf.common.utils.exception.BizException;
import com.jf.common.utils.meta.enums.GlobalErrorCodeEnum;
import com.jf.common.utils.result.BaseResult;
import com.jf.common.utils.utils.time.LocalDateTimeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 江峰
 */
@Aspect
@Slf4j
@Component
public class LockMethodInterceptor {

	@Autowired
	private DistributeLockService distributeLockService;

	/**
	 * 防止表单重复提交
	 */
	@Around("@annotation(com.jf.common.redis.annotation.ReSubmitLock)")
	public Object reSubmitLockInterceptor(ProceedingJoinPoint pjp)
			throws Throwable {

		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();

		ReSubmitLock lockAnnotaion = method.getAnnotation(ReSubmitLock.class);

		String lockKey = generateLockKey(pjp);

		log.info("redis lock key is [{}]", lockKey);

		log.info("线程 = [{}], lockKey = [{}], waitTime = [{}], leaseTime = [{}]",
				Thread.currentThread().getName(), lockKey,
				lockAnnotaion.waitTime(), lockAnnotaion.leaseTime());

		final boolean success = distributeLockService.tryLock(lockKey,
				lockAnnotaion.waitTime(), lockAnnotaion.leaseTime(),
				TimeUnit.SECONDS);

		if (!success) {
			// 重复提交异常不删除key
			throw new BizException(GlobalErrorCodeEnum.RESUBMIT);
		}

		log.info("success = [{}], 时间 = [{}]", true,
				LocalDateTimeUtil.getLocalDateTimeStr());

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

		// 当前线程名
		String threadName = Thread.currentThread().getName();

		log.info("线程 = [{}]------进入分布式锁aop------", threadName);

		// 获取该注解的实例对象
		DistributeLock lockAnnotaion = ((MethodSignature) pjp.getSignature())
				.getMethod().getAnnotation(DistributeLock.class);

		String lockKey = lockAnnotaion.lockKey();

		if (StringUtils.isEmpty(lockKey)) {
			throw new BizException("分布式锁必须指定key的值");
		}

		// 生成分布式锁key
		log.info("线程[{}]尝试获取锁，锁的key=[{}]", threadName, lockKey);

		log.info("线程 = [{}], lockKey = [{}], waitTime = [{}], leaseTime = [{}]",
				threadName, lockKey, lockAnnotaion.waitTime(),
				lockAnnotaion.leaseTime());

		if (distributeLockService.tryLock(lockKey, lockAnnotaion.waitTime(),
				lockAnnotaion.leaseTime(), TimeUnit.SECONDS)) {

			try {
				log.info("线程 = [{}] 获取锁成功", threadName);

				return pjp.proceed();
			} finally {
				if (distributeLockService.isLocked(lockKey)) {
					log.info("锁的key的值 = [{}], 被线程 = [{}]持有", lockKey,
							threadName);

					if (distributeLockService.isHeldByCurrentThread(lockKey)) {
						log.info("当前线程 {} 保持锁定", threadName);
						distributeLockService.unlock(lockKey);
						log.info("线程{} 释放锁", threadName);
					}
				}
			}
		} else {
			log.info("线程[{}] 获取锁失败", threadName);
		}
		return BaseResult.fail(GlobalErrorCodeEnum.NOT_GET_LOCK);
	}

}
