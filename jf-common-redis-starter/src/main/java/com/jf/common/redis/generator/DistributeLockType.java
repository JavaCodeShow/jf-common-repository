package com.jf.common.redis.generator;

/**
 * 分布式锁类型
 */
public interface DistributeLockType {

    /**
     * 服务名称,如：MTC_ORDER
     */
    String getPrefix();

    /**
     * 业务类型名称，如：RELEASE_ORDER
     */
    String getBizType();

}
