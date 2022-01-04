package com.jf.common.redis.service.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 该类封装了redisTemplate中一些常用的操作命令，
 * 如果使用的时候发现这个类里面没有你想要的命令，
 * 可以直接使用redisTemplate.opsForxxx即可。
 *
 * @author 江峰
 * @create 2021-03-20 23:11:05
 * @since
 */
public interface GlobalCacheService<K, V> {

    /**
     * string类型
     * 设置值
     */
    void set(K key, V value);

    /**
     * string类型
     * 设置值，含过期时间
     */
    void set(K key, V value, long expire);

    /**
     * string类型
     * get值
     */
    V get(K key);

    /**
     * 设置过期时间
     */
    boolean expire(K key, long expire);

    /**
     * 判断key是否存在
     */
    boolean exists(K key);

    /**
     * 删除key
     */
    void del(K key);

    /**
     * hash类型
     * 设置值
     */
    void hSet(K key, K field, V value);

    /**
     * hash类型
     * 获取值
     */
    Object hGet(K key, K field);

    /**
     * hash类型
     * 批量设置值
     */
    void hMSet(K key, Map<K, V> value);

    /**
     * hash类型
     * 批量获取值
     */
    Collection hMGet(String key, List fieldList);

    /**
     * set类型
     * 设置值
     */
    void sAdd(K key, V... values);

    /**
     * set类型
     * 获取值
     */
    Collection sMembers(K key);

    /**
     * set类型
     * 该key对应的值是否存在
     */
    boolean sIsMember(K key, V value);

    /**
     * 批量删除
     */
    void del(Set<K> keys);

    /**
     * redis 自增
     */
    long incr(K key, long delta);

    /**
     * 设置过期周期
     *
     * @param key    redis key
     * @param second 过期时间，单位为秒
     * @return
     */
    Boolean setExpire(K key, long second);

    /**
     * 获取过期时间
     */
    long getExpire(K key);

}
