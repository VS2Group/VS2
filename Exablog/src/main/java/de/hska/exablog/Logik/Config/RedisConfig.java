package de.hska.exablog.Logik.Config;

import de.hska.exablog.Logik.Model.Database.Messaging.Receiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Angelo on 04.12.2016.
 */
@Configuration
public class RedisConfig {
	public final static String KEY_FOR_ALL_USERS = "all_users";
	public final static String KEY_FOR_SORTED_USERS = "all_users_sorted";
	public final static String KEY_FOR_LATEST_POSTS = "latest_post_ids";
	public final static int TIMELINE_LIMIT = 10;
	public final static long SESSION_TIMEOUT_MINUTES = 30;
	private final static String SERVER_HOST_NAME = "localhost";
	private final static int SERVER_PORT = 6379;
	private final static String SERVER_PASSWORT = "";
	static private JedisConnectionFactory jRedisConnectionFactory;
	static private RedisMessageListenerContainer redisMessageListenerContainer;
	static private StringRedisTemplate stringRedisTemplate;

	@Bean(name = "redis_config")
	public static RedisConnectionFactory getConnectionFactory() {
		if (jRedisConnectionFactory == null) {
			jRedisConnectionFactory = new JedisConnectionFactory(new JedisPoolConfig());
			jRedisConnectionFactory.setHostName(SERVER_HOST_NAME);
			jRedisConnectionFactory.setPort(SERVER_PORT);
			jRedisConnectionFactory.setPassword(SERVER_PASSWORT);
		}
		return jRedisConnectionFactory;
	}

	@Bean(name = "redis_message_config")
	public static RedisMessageListenerContainer getRedisMessageListenerContainer(MessageListenerAdapter listenerAdapter) {
		if (redisMessageListenerContainer == null) {
			RedisConnectionFactory connectionFactory = getConnectionFactory();

			redisMessageListenerContainer = new RedisMessageListenerContainer();
			redisMessageListenerContainer.setConnectionFactory(connectionFactory);
			redisMessageListenerContainer.addMessageListener(listenerAdapter, new PatternTopic("neue_posts_verfuegbar"));
		}

		return redisMessageListenerContainer;
	}

	/*@Bean//(name = "stringRedisTemplate")
	public static StringRedisTemplate getStringRedisTemplate() {
		if(stringRedisTemplate == null) {
			stringRedisTemplate = new StringRedisTemplate(getConnectionFactory());
			stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
			stringRedisTemplate.setHashValueSerializer(new StringRedisSerializer());
			stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
		}
		return stringRedisTemplate;
	}*/

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	Receiver receiver(CountDownLatch latch) {
		return new Receiver(latch);
	}

	@Bean
	CountDownLatch latch() {
		return new CountDownLatch(1);
	}

}
