package com.jf.common.redis.service.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 该类封装了redisTemplate中一些常用的操作命令，
 * 如果使用的时候发现这个类里面没有你想要的命令，
 * 可以直接使用redisTemplate.opsForxxx即可。
 * key 和 value 都是String类型，
 * 如果value是对象，为了统一，便于理解，需要转换为JSON字符串
 *
 * @author 江峰
 * @create 2021-03-20 23:11:05
 */
public interface GlobalCacheService {

    /**
     * string类型
     * 设置值
     */
    void set(String key, String value);

    /**
     * string类型
     * 设置值，含过期时间
     */
    void set(String key, String value, Long expire);

    /**
     * string类型
     * get值
     */
    String get(String key);

    /**
     * string类型
     * 批量get值
     */
    List<String> mGet(List<String> keyList);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, Long expire);

    /**
     * 判断key是否存在
     */
    Boolean exists(String key);

    /**
     * 删除key
     */
    void del(String key);

    /**
     * hash类型
     * 设置值
     */
    void hSet(String key, String field, String value);

    /**
     * hash类型
     * 获取值
     */
    String hGet(String key, String field);

    /**
     * hash类型
     * 批量设置值
     */
    void hMSet(String key, Map<String, String> value);

    /**
     * hash类型
     * 批量获取值
     */
    List<String> hMGet(String key, Collection fieldList);

    /**
     * set类型
     * 设置值
     */
    void sAdd(String key, String... values);

    /**
     * set类型
     * 获取值
     */
    Set<String> sMembers(String key);

    /**
     * set类型
     * 该key对应的值是否存在
     */
    Boolean sIsMember(String key, String value);

    /**
     * 批量删除
     */
    void del(Set<String> keys);

    /**
     * redis 自增
     */
    Long incr(String key, Long delta);

    /**
     * 设置过期周期
     *
     * @param key    redis key
     * @param second 过期时间，单位为秒
     * @return
     */
    Boolean setExpire(String key, Long second);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

}
