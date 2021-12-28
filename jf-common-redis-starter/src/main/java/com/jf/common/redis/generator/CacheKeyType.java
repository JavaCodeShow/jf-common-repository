package com.jf.common.redis.generator;

/**
 * 分布式缓存key的名称枚举
 */
public interface CacheKeyType {

    /**
     * 服务名称,如：MTC_ORDER
     */
    String getPrefix();

    /**
     * 业务类型名称，如：RELEASE_ORDER
     */
    String getBizType();

}
