package com.jf.common.redis.generator;

import com.jf.common.utils.exception.BizException;
import com.jf.common.utils.meta.enums.GlobalErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 分布式缓存键生成器
 *
 * @author 江峰
 * @date 2020/6/19 11:47 上午
 */
@Slf4j
public class CacheKeyGenerator {

    /**
     * 获取key的唯一性标识前缀，采用prefix+":"+bizType格式，如：MTC_ORDER:RELEASE_ORDER
     */
    public static String generateCacheKey(CacheKeyType cacheKeyType,
                                          String bizCode) {

        if (StringUtils.isBlank(bizCode)) {
            throw new BizException(GlobalErrorCodeEnum.PARAMS_MUST);
        }
        return cacheKeyType.getPrefix() + ":" + cacheKeyType.getBizType() + ":"
                + bizCode;
    }
}
