package de.vs2group.Dao;

import de.vs2group.Entity.User;

import java.util.Collection;

/**
 * Created by Angelo on 24.11.2016.
 */
public interface UserDao {
	Collection<User> getAllUsers();

	User getUserById(int id);

	void deleteUserById(int id);

	void updateUser(User user);

	void insertUser(User user);

	boolean exists(int id);
}
