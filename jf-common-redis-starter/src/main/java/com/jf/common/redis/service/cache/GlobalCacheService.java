package com.jf.common.redis.service.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-03-20 23:11:05
 * @since
 */
public interface GlobalCacheService {

    /**
     * string 类型 设置
     */
    void set(String key, String value);

    /**
     * string 类型 设置 含过期时间
     */
    void set(String key, String value, long expire);

    /**
     * string 类型 get值
     */
    String get(String key);

    /**
     * string 类型 批量get值
     */
    List<String> multiGet(List<String> keyList);

    /**
     * string 类型 get值 并且设置为另外一个值
     */
    String getAndSet(String key, String value);

    /**
     * 设置过期时间
     */
    boolean expire(String key, long expire);

    /**
     * 判断key是否存在
     */
    boolean exists(String key);

    /**
     * 删除key
     */
    void del(String key);

    /**
     * hash 设置值
     */
    void hSet(String key, String field, String value);

    /**
     * hash 获取值
     */
    Object hGet(String key, String field);

    /**
     * 批量 hash 设置值
     */
    void hMSet(String key, Map value);

    void sAdd(String key, String value);

    boolean sIsMember(String key, String value);

    /**
     * 批量删除
     */
    void del(Set<String> keys);

    /**
     * redis 自增
     */
    long incr(String key, long delta);

    /**
     * 设置生命周期
     *
     * @param key    redis key
     * @param second 过期时间，单位为秒
     * @return
     */
    Boolean setExpire(String key, long second);

    /**
     * 获取过期时间
     */
    long getExpire(String key);

}
