package com.jf.common.redis.service.cache;

import com.alibaba.fastjson.JSON;
import com.jf.common.redis.constant.CacheKeyConstants;
import com.jf.common.redis.domain.ConcurrentProtectedCacheModel;
import com.jf.common.redis.service.lock.DistributeLockManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author 江峰
 * 并发控制抽象类
 * 如果缓存逻辑过期时间尚未过期，则获取缓存中的数据直接返回(缓存数据不存在走并发控制获取)
 * 如果缓存逻辑过期时间已过期，则走并发控制获取
 * 并发控制获取：所有线程尝试请求获取并发锁，未获取到锁的线程按照自旋策略开始自旋等待并尝试从缓存获取数据；获取到锁的线程请求实际服务获取数据，然后写入缓存并更新逻辑过期时间
 * @since 2021/12/28
 */
@Slf4j
@Component
public class ConcurrentProtectedCacheManager {

    /**
     * 缓存数据逻辑过期时间
     */
    private final int CACHE_LOGICAL_EXPIRE_SECONDS = 6;

    /**
     * 缓存数据物理过期时间
     */
    private final int CACHE_DATA_KEY_EXPIRE_SECONDS = 60;

    /**
     * 并发锁锁定过期时间
     */
    private final int CONCURRENT_CACHE_KEY_EXPIRE_SECONDS = 60;

    /**
     * 自旋等待最大次数
     */
    private final int LOOP_MAX = 5;

    /**
     * 自旋等待休眠时间间隔
     */
    private final int LOOP_INTERVAL_MILLISECONDS = 100;

    @Autowired
    private GlobalCacheManager globalCacheManager;

    @Autowired
    private DistributeLockManager DistributeLockManager;

    public <T> T get(String cacheKey, Class<?> clazz, Supplier<T> supplier) {
        return get(cacheKey, clazz, supplier, null);
    }

    public <T> T get(String cacheKey, Class<?> clazz, Supplier<T> supplier, Supplier<T> fallbackSupplier) {
        return get(cacheKey, CACHE_LOGICAL_EXPIRE_SECONDS, CACHE_DATA_KEY_EXPIRE_SECONDS, clazz, supplier, fallbackSupplier);
    }

    public <T> T get(String cacheKey, int cacheLogicalExpireSeconds, int cacheDataKeyExpireSeconds, Class<?> clazz, Supplier<T> supplier) {
        return get(cacheKey, cacheLogicalExpireSeconds, cacheDataKeyExpireSeconds, clazz, supplier, null);
    }

    public <T> T get(String cacheKey, int cacheLogicalExpireSeconds, int cacheDataKeyExpireSeconds, Class<?> clazz, Supplier<T> supplier, Supplier<T> fallbackSupplier) {
        return get(cacheKey, cacheLogicalExpireSeconds, cacheDataKeyExpireSeconds, CONCURRENT_CACHE_KEY_EXPIRE_SECONDS, clazz, supplier, fallbackSupplier);
    }

    public <T> T get(String cacheKey, int cacheLogicalExpireSeconds, int cacheDataKeyExpireSeconds, int concurrentCacheKeyExpireSeconds, Class<?> clazz, Supplier<T> supplier) {
        return get(cacheKey, cacheLogicalExpireSeconds, cacheDataKeyExpireSeconds, concurrentCacheKeyExpireSeconds, clazz, supplier, null);
    }

    public <T> T get(String cacheKey, int cacheLogicalExpireSeconds, int cacheDataKeyExpireSeconds, int concurrentCacheKeyExpireSeconds, Class<?> clazz, Supplier<T> supplier, Supplier<T> fallbackSupplier) {
        return get(cacheKey, cacheLogicalExpireSeconds, cacheDataKeyExpireSeconds, concurrentCacheKeyExpireSeconds, LOOP_MAX, LOOP_INTERVAL_MILLISECONDS, clazz, supplier, fallbackSupplier);
    }

    /**
     * cacheKey 缓存key
     * 缓存数据逻辑过期时间(默认6秒)
     * 缓存数据物理过期时间(默认60秒)
     * 并发锁锁定过期时间(默认60秒)
     * 自旋等待最大次数(默认5次)
     * 自旋等待休眠时间间隔(默认100毫秒+100毫秒以内随机数)
     * clazz 缓存对象的实际类型
     * supplier 获取数据方法
     * fallbackSupplier 获取数据降级方法
     **/
    public <T> T get(String cacheKey, int cacheLogicalExpireSeconds, int cacheDataKeyExpireSeconds, int concurrentCacheKeyExpireSeconds, int loopMax, int loopIntervalMilliSeconds, Class<?> clazz, Supplier<T> supplier, Supplier<T> fallbackSupplier) {
        Assert.notNull(supplier, "supplier参数不能为空");
        if (cacheDataKeyExpireSeconds <= 0) {
            return supplier.get();
        }

        boolean expired = checkExpired(cacheKey);
        if (expired) {
            String concurrentCacheKey = String.format("%s_CONCURRENT_KEY", cacheKey);
            boolean lock = DistributeLockManager.tryLock(concurrentCacheKey, concurrentCacheKeyExpireSeconds, TimeUnit.SECONDS);
            if (lock) {
                try {
                    T t = cacheDataFromSupplier(cacheKey, cacheLogicalExpireSeconds, cacheDataKeyExpireSeconds, supplier);
                    if (t != null) {
                        return t;
                    }
                } catch (Exception ex) {
                    log.error("并发锁获取成功后获取数据更新缓存异常!", ex);
                    throw ex;
                } finally {
                    DistributeLockManager.unlock(concurrentCacheKey);
                }
            }
        }

        //如果缓存结果为空，则所有请求都都尝试去添加并发锁控制其中一个线程去更新数据，未获取到锁的自旋等待
        //如果缓存结果不为空，则所有请求都尝试去添加并发锁控制其中一个线程去更新数据，未获取到锁的直接返回缓存数据
        //自旋等待并尝试
        int loopCounter = loopMax;
        while (loopCounter > 0) {
            loopCounter--;

            //自旋后立马尝试获取
            Object obj = getFromCache(cacheKey, clazz);
            if (Objects.nonNull(obj)) {
                return (T) obj;
            } else {
                //还未获取到sleep
                sleep(loopIntervalMilliSeconds);
            }
        }
        //保证可用
        return fallbackSupplier == null ? null : fallbackSupplier.get();
    }

    /**
     * 从缓存获取数据
     **/
    private Object getFromCache(String cacheKey, Class<?> clazz) {
        String data = globalCacheManager.hGet(cacheKey, CacheKeyConstants.CACHE_DATA_FIELD);
        if (data == null) {
            return null;
        }
        ConcurrentProtectedCacheModel cacheModel = JSON.parseObject(data, ConcurrentProtectedCacheModel.class);
        String jsonString = cacheModel.getJsonString();
        if (cacheModel.getIsCollection()) {
            return JSON.parseArray(jsonString, clazz);
        }
        return JSON.parseObject(jsonString, clazz);
    }

    /**
     * 获取数据并缓存
     **/
    private <T> T cacheDataFromSupplier(String cacheKey, int cacheLogicalExpireSeconds, int cacheDataKeyExpireSeconds, Supplier<T> supplier) {
        T t = supplier.get();
        if (t == null) {
            return null;
        }
        ConcurrentProtectedCacheModel cacheModel = getConcurrentProtectedCacheModel(t);
        Map<String, String> resultMap = getResultMap(cacheLogicalExpireSeconds, cacheModel);
        globalCacheManager.hMSet(cacheKey, resultMap);
        globalCacheManager.setExpire(cacheKey, (long) cacheDataKeyExpireSeconds);
        return t;
    }

    /**
     * 组装缓存数据
     **/
    private Map<String, String> getResultMap(int cacheLogicalExpireSeconds, ConcurrentProtectedCacheModel cacheModel) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(CacheKeyConstants.CACHE_DATA_FIELD, JSON.toJSONString(cacheModel));
        resultMap.put(CacheKeyConstants.CACHE_EXPIRE_TIMESTAMP_FIELD, String.valueOf(new Date().getTime() + cacheLogicalExpireSeconds * 1000));
        return resultMap;
    }

    /**
     * 获取缓存对象
     **/
    private <T> ConcurrentProtectedCacheModel getConcurrentProtectedCacheModel(T t) {
        ConcurrentProtectedCacheModel cacheModel = new ConcurrentProtectedCacheModel();
        cacheModel.setJsonString(JSON.toJSONString(t));
        cacheModel.setIsCollection(false);
        Class<?> tClass = t.getClass();
        if (tClass.isArray() || Collection.class.isAssignableFrom(tClass)) {
            cacheModel.setIsCollection(true);
        }
        return cacheModel;
    }

    /**
     * 休眠
     **/
    private void sleep(int loopIntervalMilliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(loopIntervalMilliSeconds + RandomUtils.nextInt(0, loopIntervalMilliSeconds));
        } catch (InterruptedException e) {
            log.warn("获取数据后休眠异常！", e);
        }
    }

    /**
     * 校验数据是否过期
     **/
    private boolean checkExpired(String cacheDataKey) {
        String time = globalCacheManager.hGet(cacheDataKey, CacheKeyConstants.CACHE_EXPIRE_TIMESTAMP_FIELD);
        if (StringUtils.isNotBlank(time)) {
            return new Date().getTime() - Long.parseLong(time) >= 0;
        }
        return true;
    }
}
