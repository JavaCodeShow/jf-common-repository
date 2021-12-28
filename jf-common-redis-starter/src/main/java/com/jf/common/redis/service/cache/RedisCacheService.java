package com.jf.common.redis.service.cache;

import java.util.Map;
import java.util.Set;

/**
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-03-20 23:11:05
 * @since
 */
public interface RedisCacheService {

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
	void del(Set<String> keys);

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

}
