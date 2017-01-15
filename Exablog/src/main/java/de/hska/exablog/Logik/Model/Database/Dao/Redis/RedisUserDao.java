package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Exception.UserAlreadyExistsException;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Database.Dao.IUserDao;
import de.hska.exablog.Logik.Model.Database.RedisDatabase;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Angelo on 04.12.2016.
 */
@Repository
@Qualifier("RedisDatabase")
public class RedisUserDao implements IUserDao {

	@Autowired
	private RedisDatabase database;
	@Autowired
	private UserService userService;

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
				.setUserService(userService)
				.build();
	}

	@Override
	public List<User> searchForUsers(String searchTerm) {
		Set<String> users = database.getAllUsersOps().members(RedisConfig.KEY_FOR_ALL_USERS);

		List<User> usersFound = new ArrayList<>();

		for (String user : users) {
			String username = user.split(":")[1];

			if (username.toLowerCase().contains(searchTerm.toLowerCase())) {
				try {
					usersFound.add(getUserByName(username));
				} catch (UserDoesNotExistException e) {
					e.printStackTrace();
				}
			}
		}

		return usersFound;
	}

	@Override
	@Deprecated
	public void removeUserByName(String username) throws UserDoesNotExistException {
		String userKey = "user:" + username;

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new UserDoesNotExistException(username, true);
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getUserDataOps().delete(userKey, "username", "firstname", "lastname", "imageurl");

		String loginKey = "login:" + username;
		database.getRegisteredLoginsOps().delete(loginKey, "password");

		String simpleLoginTokenKey = "session:" + username;

		database.getCurrentLoginsOps().set(simpleLoginTokenKey, "", 0, TimeUnit.MINUTES);

		database.getAllUsersOps().remove(RedisConfig.KEY_FOR_ALL_USERS, userKey);
	}

	@Override
	public void updateUser(User user) throws UserDoesNotExistException {

		String userKey = "user:" + user.getUsername();

		if (!database.getAllUsersOps().isMember(RedisConfig.KEY_FOR_ALL_USERS, userKey)) {
			throw new UserDoesNotExistException(user.getUsername(), false);
		}

		database.getAllUsersOps().add(RedisConfig.KEY_FOR_ALL_USERS, userKey);
		database.getAllUsersSortedOps().add(RedisConfig.KEY_FOR_SORTED_USERS, userKey, 0.0);
		database.getUserDataOps().put(userKey, "username", user.getUsername());
		database.getUserDataOps().put(userKey, "firstname", user.getFirstName());
		database.getUserDataOps().put(userKey, "lastname", user.getLastName());
		database.getUserDataOps().put(userKey, "password", user.getPassword()); // TODO: encrypt password
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
		database.getAllUsersSortedOps().add(RedisConfig.KEY_FOR_SORTED_USERS, userKey, 0.0);
		database.getUserDataOps().put(userKey, "username", user.getUsername());
		database.getUserDataOps().put(userKey, "firstname", user.getFirstName());
		database.getUserDataOps().put(userKey, "lastname", user.getLastName());
		database.getUserDataOps().put(userKey, "password", user.getPassword()); // TODO: encrypt password
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
				.setUserService(userService)
				.build();
	}

	@Override
	public Collection<User> getFollowings(User user) {
		String key = user.getUsername() + ":following";
		return extractUsers(database.getUserFollowedOps().members(key));
	}

	@Override
	public Collection<User> getFollowers(User user) {
		String key = user.getUsername() + ":follower";
		return extractUsers(database.getUserFollowersOps().members(key));
	}

	@Override
	public boolean isFollowing(User from, User to) {
		return this.getFollowings(from).contains(to);
	}

	@Override
	public void follow(User from, User to) {
		Collection<User> followings = this.getFollowings(from);
		boolean containsTo = followings.contains(to);

		if (!containsTo) {
			database.getUserFollowersOps().add(to.getUsername() + ":follower", from.getUsername());
			database.getUserFollowedOps().add(from.getUsername() + ":following", to.getUsername());
		}
	}

	@Override
	public void unfollow(User from, User to) {
		if (this.getFollowings(from).contains(to)) {
			database.getUserFollowersOps().remove(to.getUsername() + ":follower", from.getUsername());
			database.getUserFollowedOps().remove(from.getUsername() + ":following", to.getUsername());
		}
	}

	private Set<User> extractUsers(Set<String> userNames) {
		Set<User> users = new HashSet<>();
		for (String userName : userNames) {
			try {
				users.add(getUserByName(userName));
			} catch (UserDoesNotExistException e) {
				e.printStackTrace(); // throw a wrapping uncaught exception?
			}
		}
		return users;
	}


}
