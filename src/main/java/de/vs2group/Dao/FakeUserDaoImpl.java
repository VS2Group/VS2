package de.vs2group.Dao;


import de.vs2group.Entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("fakeData")
public class FakeUserDaoImpl implements UserDao {
	private static Map<Integer, User> users;

	static {
		users = new HashMap<Integer, User>(){
			{
				put(1, new User(1, "Tom", "tom@sawyer.com", "123456"));
				put(2, new User(2, "Lisa", "lisa@sawyer.com", "123456"));
				put(3, new User(3, "Ben", "ben@sawyer.com", "123456"));
			}
		};
	}

	@Override
	public Collection<User> getAllUsers() {
		return users.values();
	}

	@Override
	public User getUserById(int id) {
		return users.get(id);
	}

	@Override
	public void deleteUserById(int id) {
		users.remove(id);
	}

	@Override
	public void updateUser(User user) {
		User u = users.get(user.getId());
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		users.put(user.getId(), u);
	}

	@Override
	public void insertUser(User user) {
		users.put(user.getId(), user);
	}

	@Override
	public boolean exists(int id) {
		return users.containsKey(id);
	}
}
