package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Exception.UserAlreadyExistsException;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Database.Dao.IUserDao;
import de.hska.exablog.Logik.Model.Database.RedisDatabase;
import de.hska.exablog.Logik.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Angelo on 04.12.2016.
 */
@Repository
@Qualifier("RedisDatabase")
public class RedisUserDao implements IUserDao {

	@Autowired
	private RedisDatabase database;

	@Override
	public User getUserByName(String username) throws UserDoesNotExistException {
		String key = "user:" + username;

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, key)) {
			throw new UserDoesNotExistException();
		}

		return User.getBuilder()
				.setUsername(database.getUserDataOps().get(key, "username"))
				.setPassword(database.getUserDataOps().get(key, "password"))
				.setFirstName(database.getUserDataOps().get(key, "firstname"))
				.setLastName(database.getUserDataOps().get(key, "lastname"))
				.setImageUrl(database.getUserDataOps().get(key, "imageurl"))
				.build();
	}

	@Override
	public void removeUserByName(String username) throws UserDoesNotExistException {
		String userKey = "user:" + username;

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new UserDoesNotExistException();
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getUserDataOps().delete(userKey, "username", "firstname", "lastname", "imageurl");

		String loginKey = "login:" + username;
		database.getRegisteredLoginsOps().delete(loginKey, "password"); // TODO: encrypt password

		String simpleLoginTokenKey = "session:" + username;

		database.getCurrentLoginsOps().set(simpleLoginTokenKey, "", 0, TimeUnit.MINUTES);

		database.getAllUsersOps().remove(RedisConfig.KEY_FOR_ALL_USERS, userKey);
	}

	@Override
	public void updateUser(User user) throws UserDoesNotExistException {

		String userKey = "user:" + user.getUsername();

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new UserDoesNotExistException();
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getUserDataOps().put(userKey, "username", user.getUsername());
		database.getUserDataOps().put(userKey, "firstname", user.getFirstName());
		database.getUserDataOps().put(userKey, "lastname", user.getLastName());
		database.getUserDataOps().put(userKey, "password", user.getPassword());
		database.getUserDataOps().put(userKey, "imageurl", user.getImageUrl());

		String simpleLoginTokenKey = "session:" + user.getUsername();
		String simpleLoginToken = user.getUsername() + ":" + user.getFirstName() + ":" + user.getLastName();

		database.getCurrentLoginsOps().set(simpleLoginTokenKey, simpleLoginToken, RedisConfig.SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
	}

	@Override
	public User insertUser(User user) throws UserAlreadyExistsException {
		String userKey = "user:" + user.getUsername();

		if (database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new UserAlreadyExistsException();
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getUserDataOps().put(userKey, "username", user.getUsername());
		database.getUserDataOps().put(userKey, "firstname", user.getFirstName());
		database.getUserDataOps().put(userKey, "lastname", user.getLastName());
		database.getUserDataOps().put(userKey, "password", user.getPassword());
		database.getUserDataOps().put(userKey, "imageurl", user.getImageUrl());

		String simpleLoginTokenKey = "session:" + user.getUsername();
		String simpleLoginToken = user.getUsername() + ":" + user.getFirstName() + ":" + user.getLastName();

		database.getCurrentLoginsOps().set(simpleLoginTokenKey, simpleLoginToken, RedisConfig.SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);

		return User.getBuilder()
				.setUsername(user.getUsername())
				.setFirstName(user.getFirstName())
				.setLastName(user.getLastName())
				.setPassword(user.getPassword())
				.setImageUrl(user.getImageUrl())
				.build();
	}

	@Override
	public Collection<User> getFollowed(User user) {
		String key = user.getUsername() + ":following";
		return extractUsers(database.getUserFollowedOps().members(key));
	}

	@Override
	public Iterable<User> getFollowers(User user) {
		String key = user.getUsername() + ":follower";
		return extractUsers(database.getUserFollowersOps().members(key));
	}

	private Set<User> extractUsers(Set<String> userNames) {
		Set<User> users = new HashSet<>();
		for (String userName : userNames) {
			try {
				users.add(getUserByName(userName));
			} catch (UserDoesNotExistException e) {
				e.printStackTrace();
			}
		}
		return users;
	}

	@Override
	public boolean toggleFollowing(User follower, User following) {
		if (getFollowed(follower).contains(following)) {
			database.getUserFollowersOps().remove(following.getUsername() + ":follower", follower.getUsername());
			database.getUserFollowedOps().remove(follower.getUsername() + ":following", following.getUsername());
			return false;
		} else {
			database.getUserFollowersOps().add(following.getUsername() + ":follower", follower.getUsername());
			database.getUserFollowedOps().add(follower.getUsername() + ":following", following.getUsername());
			return true;
		}
	}

}
