package com.jf.common.redis.lock;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 分布式锁键生成器
 *
 * @author 江峰
 * @date 2020/6/19 11:47 上午
 */
public interface CacheKeyGenerator {
	/**
	 * 获取AOP参数,生成指定缓存Key
	 *
	 * @param pjp
	 *            PJP
	 * @return 缓存KEY
	 */
	String getLockKey(ProceedingJoinPoint pjp);
}
