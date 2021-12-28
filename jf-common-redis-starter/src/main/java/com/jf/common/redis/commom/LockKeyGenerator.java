package com.jf.common.redis.commom;

import org.apache.commons.lang3.StringUtils;

import com.jf.common.redis.service.lock.DistributeLockType;

/**
 * 分布式锁键生成器
 *
 * @author 江峰
 * @date 2020/6/19 11:47 上午
 */
public class LockKeyGenerator {

	/**
	 * 获取锁的唯一性标识前缀，采用prefix+":"+bizType格式，如：MTC_ORDER:RELEASE_ORDER
	 */
	public static String generateLockKey(DistributeLockType bizType,
			String bizCode) {

		if (StringUtils.isBlank(bizCode)) {
			return bizType.getPrefix() + ":" + bizType.getBizType();
		}

		return bizType.getPrefix() + ":" + bizType.getBizType() + ":" + bizCode;

	}
}
