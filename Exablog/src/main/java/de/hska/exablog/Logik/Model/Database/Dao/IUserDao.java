package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Exception.AlreadyExistsException;
import de.hska.exablog.Logik.Exception.DoesNotExistException;
import de.hska.exablog.Logik.Model.Entity.User;

import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface IUserDao {
	User getUserByName(String username);
	void removeUserByName(String username) throws DoesNotExistException;
	void updateUser(User user) throws DoesNotExistException;

	User insertUser(User user) throws AlreadyExistsException;
	Collection<User> getFollowed(User user);
	Iterable<User> getFollowers(User user);

	boolean toggleFollowing(User follower, User following);
}
