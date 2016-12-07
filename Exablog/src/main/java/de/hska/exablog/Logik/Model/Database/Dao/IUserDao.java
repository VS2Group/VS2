package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Exception.UserAlreadyExistsException;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Entity.User;

import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface IUserDao {
	User getUserByName(String username) throws UserDoesNotExistException;

	void removeUserByName(String username) throws UserDoesNotExistException;

	void updateUser(User user) throws UserDoesNotExistException;

	User insertUser(User user) throws UserAlreadyExistsException;
	Collection<User> getFollowed(User user);
	Iterable<User> getFollowers(User user);

	boolean toggleFollowing(User follower, User following);
}
