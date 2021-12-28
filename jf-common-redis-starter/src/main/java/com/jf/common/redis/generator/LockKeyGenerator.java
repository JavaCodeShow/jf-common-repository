package com.jf.common.redis.generator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 分布式锁键生成器
 *
 * @author 江峰
 * @date 2020/6/19 11:47 上午
 */
@Slf4j
public class LockKeyGenerator {

    /**
     * 获取锁的唯一性标识前缀，采用prefix+":"+bizType格式，如：MTC_ORDER:RELEASE_ORDER
     */
    public static String generateLockKey(DistributeLockType lockType,
                                         String bizCode) {

        if (StringUtils.isBlank(bizCode)) {
            return lockType.getPrefix() + ":" + lockType.getBizType();
        }
        return lockType.getPrefix() + ":" + lockType.getBizType() + ":"
                + bizCode;
    }
}
