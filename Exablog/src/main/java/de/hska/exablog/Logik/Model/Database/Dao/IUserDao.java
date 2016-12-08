package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Exception.UserAlreadyExistsException;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface IUserDao {
	User getUserByName(String username) throws UserDoesNotExistException;

	List<User> searchForUsers(String searchTerm);

	void removeUserByName(String username) throws UserDoesNotExistException;

	void updateUser(User user) throws UserDoesNotExistException;

	User insertUser(User user) throws UserAlreadyExistsException;

	Collection<User> getFollowings(User user);

	Collection<User> getFollowers(User user);

	boolean isFollowing(User from, User to);

	void follow(User from, User to);

	void unfollow(User from, User to);
}
