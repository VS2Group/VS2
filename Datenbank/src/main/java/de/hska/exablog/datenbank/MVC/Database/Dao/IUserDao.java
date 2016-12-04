package de.hska.exablog.datenbank.MVC.Database.Dao;

import de.hska.exablog.datenbank.MVC.Entity.User;

import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface IUserDao {
	User getUserByName(String username);
	void removeUserByName(String username);
	void updateUser(User user);
	void insertUser(User user);
	Collection<User> getFollowed(User user);
	Iterable<User> getFollowers(User user);

	boolean toggleFollowing(User follower, User following);
}
