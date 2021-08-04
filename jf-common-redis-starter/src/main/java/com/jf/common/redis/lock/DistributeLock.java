package com.jf.common.redis.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: 分布式锁自定义注解
 *
 * @author: 江峰
 * @create: 2021-03-23 18:51
 * @since: 2.22.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributeLock {

    /**
     * 指定分布式锁的key
     */
    String lockKey();

    /**
     * 等待获取锁的时间 <br>
     * 默认2秒钟没有获取到锁，则放弃获取锁
     */
    long waitTime() default 2;

    /**
     * 持有该锁的时间 <br>
     * 默认60秒钟客户端没有主动解锁，则主动释放锁
     */
    long leaseTime() default 60;

}