package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Model.Entity.User;

import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface IUserDao {
	User getUserByName(String username);
	void removeUserByName(String username);
	void updateUser(User user);

	User insertUser(User user);
	Collection<User> getFollowed(User user);
	Iterable<User> getFollowers(User user);

	boolean toggleFollowing(User follower, User following);
}
