package com.jf.common.redis.domain;

import lombok.Data;

/**
 * @author cuibo
 * @since 2022/1/28
 */
@Data
public class ConcurrentProtectedCacheModel {

    private String jsonString;

    private Boolean isCollection;

}
