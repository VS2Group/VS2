package de.hska.exablog.Logik.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Angelo on 04.12.2016.
 */
@Configuration
public class RedisConfig {
	private final static String SERVER_HOST_NAME = "localhost";
	private final static int SERVER_PORT = 6379;
	private final static String SERVER_PASSWORT = "";

	public final static String KEY_FOR_ALL_USERS = "all_users";
	public final static String KEY_FOR_LATEST_POSTS = "latest_post_ids";
	public final static int TIMELINE_LIMIT = 10;
	public final static long SESSION_TIMEOUT_MINUTES = 30;

	@Bean
	public RedisConnectionFactory getConnectionFactory() {
		JedisConnectionFactory jRedisConnectionFactory = new JedisConnectionFactory(new JedisPoolConfig());
		jRedisConnectionFactory.setHostName(SERVER_HOST_NAME);
		jRedisConnectionFactory.setPort(SERVER_PORT);
		jRedisConnectionFactory.setPassword(SERVER_PASSWORT);
		return jRedisConnectionFactory;
	}

	@Bean(name = "stringRedisTemplate")
	public StringRedisTemplate getStringRedisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(getConnectionFactory());
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setHashValueSerializer(new StringRedisSerializer());
		stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
		return stringRedisTemplate;
	}

}
