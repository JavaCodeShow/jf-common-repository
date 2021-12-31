package com.jf.common.redis.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-03-20 23:11:05
 * @since
 */
@Service
@Slf4j
public class GlobalCacheServiceImpl implements GlobalCacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(final String key, final String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(final String key, final String value, long expire) {
        stringRedisTemplate.opsForValue().set(key, value, expire,
                TimeUnit.SECONDS);
    }

    @Override
    public String get(final String key) {

        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> multiGet(List<String> keyList) {
        return stringRedisTemplate.opsForValue().multiGet(keyList);
    }

    @Override
    public String getAndSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {

        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public void del(String key) {

        stringRedisTemplate.delete(key);
    }

    @Override
    public void del(Set<String> keys) {

        stringRedisTemplate.delete(keys);
    }

    @Override
    public void hSet(String key, String field, String value) {

        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public void hMSet(String key, Map value) {

        stringRedisTemplate.opsForHash().putAll(key, value);
    }

    @Override
    public void sAdd(String key, String value) {

        stringRedisTemplate.opsForSet().add(key, value);
    }

    @Override
    public boolean sIsMember(String key, String value) {

        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Object hGet(String key, String field) {
        return stringRedisTemplate.opsForHash().get(key, field);
    }

    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Boolean setExpire(String key, long second) {
        return stringRedisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    @Override
    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

}
