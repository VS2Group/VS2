package de.vs2group.Service;

import de.vs2group.Dao.FakeUserDaoImpl;
import de.vs2group.Dao.UserDao;
import de.vs2group.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

	@Autowired
	@Qualifier("fakeData")
	private UserDao userDao;

	public Collection<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	public void deleteUserById(int id) {
		userDao.deleteUserById(id);
	}

	public void updateUser(User user) {
		if(!userDao.exists(user.getId())) {
			throw new IllegalStateException("User does not exist!");
		}
		userDao.updateUser(user);
	}

	public void insertUser(User user) {
		if(userDao.exists(user.getId())) {
			throw new IllegalStateException("User does already exist!");
		}
		userDao.insertUser(user);
	}
}
