package de.hska.exablog.datenbank.MVC.Database.Dao.Redis;

import de.hska.exablog.datenbank.Config.RedisConfig;
import de.hska.exablog.datenbank.Exceptions.AlreadyExistsException;
import de.hska.exablog.datenbank.Exceptions.DoesNotExistException;
import de.hska.exablog.datenbank.MVC.Database.Dao.IUserDao;
import de.hska.exablog.datenbank.MVC.Database.RedisDatabase;
import de.hska.exablog.datenbank.MVC.Entity.User;
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
	public User getUserByName(String username) {
		String key = "user:" + username;

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, key)) {
			return null;
		}

		return User.getBuilder()
				.setUsername(username)
				.setPassword(database.getUserDataOps().get(key, "password"))
				.setFirstName(database.getUserDataOps().get(key, "firstname"))
				.setLastName(database.getUserDataOps().get(key, "lastname"))
				.build();
	}

	@Override
	public void removeUserByName(String username) {
		String userKey = "user:" + username;

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new DoesNotExistException("The User " + username + " does not exist.");
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getUserDataOps().delete(userKey, "username", "firstname", "lastname");

		String loginKey = "login:" + username;
		database.getRegisteredLoginsOps().delete(loginKey, "password", "bucquestion", "bucanswer"); // TODO: encrypt password

		String simpleLoginTokenKey = "session:" + username;

		database.getCurrentLoginsOps().set(simpleLoginTokenKey, "", 0, TimeUnit.MINUTES);

		database.getAllUsersOps().remove(RedisConfig.KEY_FOR_ALL_USERS, userKey);
	}

	@Override
	public void updateUser(User user) {

		String userKey = "user:" + user.getUsername();

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new DoesNotExistException("The User " + user.getUsername() + " does not exist.");
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getUserDataOps().put(userKey, "username", user.getUsername());
		database.getUserDataOps().put(userKey, "firstname", user.getFirstName());
		database.getUserDataOps().put(userKey, "lastname", user.getLastName());

		String loginKey = "login:" + user.getUsername();
		database.getRegisteredLoginsOps().put(loginKey, "password", user.getPassword()); // TODO: encrypt password
		database.getRegisteredLoginsOps().put(loginKey, "bucquestion", user.getBackupCredentialQuestion());
		database.getRegisteredLoginsOps().put(loginKey, "bucanswer", user.getBackupCredentialAnswer());

		String simpleLoginTokenKey = "session:" + user.getUsername();
		String simpleLoginToken = user.getUsername() + ":" + user.getFirstName() + ":" + user.getLastName();

		database.getCurrentLoginsOps().set(simpleLoginTokenKey, simpleLoginToken, RedisConfig.SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
	}

	@Override
	public void insertUser(User user) {
		String userKey = "user:" + user.getUsername();

		if (database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new AlreadyExistsException("The User " + user.getUsername() + " is already registered. Please choose a different name.");
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getUserDataOps().put(userKey, "username", user.getUsername());
		database.getUserDataOps().put(userKey, "firstname", user.getFirstName());
		database.getUserDataOps().put(userKey, "lastname", user.getLastName());

		String loginKey = "login:" + user.getUsername();
		database.getRegisteredLoginsOps().put(loginKey, "password", user.getPassword()); // TODO: encrypt password
		database.getRegisteredLoginsOps().put(loginKey, "bucquestion", user.getBackupCredentialQuestion());
		database.getRegisteredLoginsOps().put(loginKey, "bucanswer", user.getBackupCredentialAnswer());

		String simpleLoginTokenKey = "session:" + user.getUsername();
		String simpleLoginToken = user.getUsername() + ":" + user.getFirstName() + ":" + user.getLastName();

		database.getCurrentLoginsOps().set(simpleLoginTokenKey, simpleLoginToken, RedisConfig.SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
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
			users.add(getUserByName(userName));
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
