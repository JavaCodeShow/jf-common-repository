package com.jf.common.redis.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @create 2021-03-20 23:11:05
 */
@Service
@Slf4j
public class GlobalCacheManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }


    public void set(final String key, final String value, Long expire) {
        redisTemplate.opsForValue().set(key, value, expire,
                TimeUnit.SECONDS);
    }


    public String get(String key) {

        return redisTemplate.opsForValue().get(key);
    }


    public List<String> mGet(List<String> keyList) {
        return redisTemplate.opsForValue().multiGet(keyList);
    }


    public Boolean expire(final String key, Long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }


    public Boolean exists(String key) {

        return redisTemplate.hasKey(key);
    }


    public void del(String key) {

        redisTemplate.delete(key);
    }


    public void del(Set<String> keys) {

        redisTemplate.delete(keys);
    }


    public void hSet(String key, String field, String value) {

        redisTemplate.opsForHash().put(key, field, value);
    }


    public void sAdd(String key, String... values) {

        redisTemplate.opsForSet().add(key, values);
    }


    public Set<String> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }


    public Boolean sIsMember(String key, String value) {

        return redisTemplate.opsForSet().isMember(key, value);
    }


    public String hGet(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }


    public void hMSet(String key, Map<String, String> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }


    public List<String> hMGet(String key, Collection fieldList) {
        return redisTemplate.opsForHash().multiGet(key, fieldList);
    }


    public Long incr(String key, Long delta) {
        assert key != null;
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }


    public Boolean setExpire(String key, Long second) {
        return redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

}
