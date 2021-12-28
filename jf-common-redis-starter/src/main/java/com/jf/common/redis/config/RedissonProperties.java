package com.jf.common.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述:
 *
 * @author: 江峰
 * @create: 2021-03-24 13:42
 * @since: 2.22.1
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

	/**
	 * redis连接地址 <br>
	 * String address = "redis://127.0.0.1:6379"
	 */
	private String address;

}
