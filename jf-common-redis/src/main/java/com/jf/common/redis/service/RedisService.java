package com.jf.common.redis.service;

import java.util.Map;
import java.util.Set;

/**
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-03-20 23:11:05
 * @since
 */
public interface RedisService {

	/**
	 * string 类型 设置
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	void set(String key, String value);

	/**
	 * string 类型 设置 含过期时间
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	void set(String key, String value, long expire);

	/**
	 * string 类型 get值
	 *
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 设置过期时间
	 *
	 * @param key
	 * @param expire
	 * @return
	 */
	boolean expire(String key, long expire);

	/**
	 * 判断key是否存在
	 *
	 * @param key
	 * @return
	 */
	boolean exists(String key);

	/**
	 * 删除key
	 *
	 * @param key
	 * @return
	 */
	void del(String key);

	/**
	 * hash 设置值
	 *
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	void hSet(String key, String field, String value);

	/**
	 * hash 获取值
	 *
	 * @param key
	 * @param field
	 * @return
	 */
	Object hGet(String key, String field);

	/**
	 * 批量 hash 设置值
	 *
	 * @param key
	 * @param value
	 */
	void hMSet(String key, Map value);

	void sAdd(String key, String value);

	boolean sIsMember(String key, String value);

	/**
	 * 批量删除
	 *
	 * @param keys
	 */
	public void del(Set<String> keys);

	/**
	 * redis 自增
	 *
	 * @param key
	 * @param delta
	 * @return
	 */
	long incr(String key, long delta);

	/**
	 * 设置生命周期
	 *
	 * @param key
	 *            redis key
	 * @param second
	 *            过期时间，单位为秒
	 * @return
	 */
	Boolean setExpire(String key, long second);

	/**
	 * 获取过期时间
	 *
	 * @param key
	 *            redis key
	 * @return
	 */
	long getExpire(String key);

	/**
	 * 获取锁
	 * 
	 * @param expire
	 *            过期时间 单位秒
	 * @param lockKey
	 *            key
	 * @param lockValue
	 *            key的值，一般是当前时间毫秒
	 * @return lockValue ==> 值不为空, 获取到锁，值为空,没有获取到锁)
	 */
	boolean tryLock(String lockKey, String lockValue, int expire);

	/**
	 * 解锁
	 *
	 * @param lockKey
	 *            key
	 * @param expireTimeStr
	 *            key的值
	 */
	void unLock(String lockKey, String expireTimeStr);

	/**
	 * 设置值，支持兵法设置
	 *
	 * @param lockKey
	 *            redisKey
	 * @param lockValue
	 *            key的value
	 * @param expire
	 *            过期时间
	 */
	void setIfAbsent(String lockKey, String lockValue, long expire);

}
