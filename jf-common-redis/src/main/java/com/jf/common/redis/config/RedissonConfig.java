package com.jf.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * 描述: redisson bean管理
 *
 * @author: 江峰
 * @create: 2021-03-23 18:40
 * @since: 2.22.1
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfig {

	@Autowired
	private RedissonProperties redissonProperties;

	/**
	 * Redisson客户端注册 单机模式
	 */
	@Bean(destroyMethod = "shutdown")
	public RedissonClient createRedissonClient() {
		Assert.notNull(redissonProperties.getAddress(),
				"redisson.address 不能为空，请在项目中配置属性redisson.address");
		Config config = new Config();
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setAddress(redissonProperties.getAddress());
		return Redisson.create(config);
	}

}