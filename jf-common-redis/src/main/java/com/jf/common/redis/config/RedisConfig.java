package com.jf.common.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-03-21 00:57:18
 * @since
 */
@Configuration
public class RedisConfig {

	private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();

	@Bean
	public RedisTemplate<String, Object> redisTemplate(
			LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(lettuceConnectionFactory);
		// key序列化方式
		template.setKeySerializer(STRING_SERIALIZER);
		// value序列化
		template.setValueSerializer(STRING_SERIALIZER);
		// key hashmap序列化
		template.setHashKeySerializer(STRING_SERIALIZER);
		// value hashmap序列化
		template.setHashValueSerializer(STRING_SERIALIZER);
		return template;
	}

}
