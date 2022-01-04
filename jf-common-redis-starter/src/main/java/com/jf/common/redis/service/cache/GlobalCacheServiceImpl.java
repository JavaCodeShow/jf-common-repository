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
public class GlobalCacheServiceImpl implements GlobalCacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(final String key, final String value, Long expire) {
        redisTemplate.opsForValue().set(key, value, expire,
                TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {

        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> mGet(List<String> keyList) {
        return redisTemplate.opsForValue().multiGet(keyList);
    }

    @Override
    public Boolean expire(final String key, Long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public Boolean exists(String key) {

        return redisTemplate.hasKey(key);
    }

    @Override
    public void del(String key) {

        redisTemplate.delete(key);
    }

    @Override
    public void del(Set<String> keys) {

        redisTemplate.delete(keys);
    }

    @Override
    public void hSet(String key, String field, String value) {

        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public void sAdd(String key, String... values) {

        redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Set<String> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Boolean sIsMember(String key, String value) {

        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public String hGet(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public void hMSet(String key, Map<String, String> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    @Override
    public List<String> hMGet(String key, Collection fieldList) {
        return redisTemplate.opsForHash().multiGet(key, fieldList);
    }

    @Override
    public Long incr(String key, Long delta) {
        assert key != null;
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Boolean setExpire(String key, Long second) {
        return redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

}
