package de.hska.exablog.Logik.Model.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

/**
 * @author ehx-v1
 */
@Repository
public class RedisDatabase {

	private HashOperations<String, String, String> userDataOps;
	private SetOperations<String, String> allUsersOps;
	private ZSetOperations<String, String> allUsersSortedOps;

	private ValueOperations<String, String> sessionUserOps;

	private HashOperations<String, String, String> postDataOps;
	private SetOperations<String, String> userPostsOps;
	private SetOperations<String, String> latestPostsOps;

	private SetOperations<String, String> userFollowersOps;
	private SetOperations<String, String> userFollowedOps;

	private HashOperations<String, String, String> registeredLoginsOps;
	private ValueOperations<String, String> currentLoginsOps;

	private RedisAtomicLong postIDOps;
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	public RedisDatabase(StringRedisTemplate stringTemplate) {
		stringRedisTemplate = stringTemplate;
		userDataOps = stringTemplate.opsForHash();
		allUsersOps = stringTemplate.opsForSet();
		allUsersSortedOps = stringTemplate.opsForZSet();
		postDataOps = stringTemplate.opsForHash();
		userPostsOps = stringTemplate.opsForSet();
		latestPostsOps = stringTemplate.opsForSet();
		userFollowersOps = stringTemplate.opsForSet();
		userFollowedOps = stringTemplate.opsForSet();
		registeredLoginsOps = stringTemplate.opsForHash();
		currentLoginsOps = stringTemplate.opsForValue();
		sessionUserOps = stringTemplate.opsForValue();

		this.postIDOps = new RedisAtomicLong("postid", stringTemplate.getConnectionFactory());
	}


	public HashOperations<String, String, String> getUserDataOps() {
		return userDataOps;
	}

	public SetOperations<String, String> getAllUsersOps() {
		return allUsersOps;
	}

	public HashOperations<String, String, String> getPostDataOps() {
		return postDataOps;
	}

	public SetOperations<String, String> getUserPostsOps() {
		return userPostsOps;
	}

	public SetOperations<String, String> getLatestPostsOps() {
		return latestPostsOps;
	}

	public SetOperations<String, String> getUserFollowersOps() {
		return userFollowersOps;
	}

	public SetOperations<String, String> getUserFollowedOps() {
		return userFollowedOps;
	}

	public HashOperations<String, String, String> getRegisteredLoginsOps() {
		return registeredLoginsOps;
	}

	public ValueOperations<String, String> getCurrentLoginsOps() {
		return currentLoginsOps;
	}

	public RedisAtomicLong getPostIDOps() {
		return postIDOps;
	}

	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}

	public ValueOperations<String, String> getSessionUserOps() {
		return sessionUserOps;
	}

	public ZSetOperations<String, String> getAllUsersSortedOps() {
		return allUsersSortedOps;
	}
}
