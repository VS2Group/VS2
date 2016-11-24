package de.vs2group.Dao;

import de.vs2group.Entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO: Implement methods
 */
@Repository
@Qualifier("redisData")
public class RedisUserDaoImpl implements UserDao {
	@Override
	public Collection<User> getAllUsers() {
		return new ArrayList<User>() {
			{
				add(new User(1, "kevin", "kevin@allein-zuhaus.de", "123456"));
			}
		};
	}

	@Override
	public User getUserById(int id) {
		return null;
	}

	@Override
	public void deleteUserById(int id) {

	}

	@Override
	public void updateUser(User user) {

	}

	@Override
	public void insertUser(User user) {

	}

	@Override
	public boolean exists(int id) {
		return false;
	}
}
