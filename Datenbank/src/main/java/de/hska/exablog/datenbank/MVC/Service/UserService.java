package de.hska.exablog.datenbank.MVC.Service;

import de.hska.exablog.datenbank.MVC.Database.Dao.IUserDao;
import de.hska.exablog.datenbank.MVC.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class UserService {

	@Autowired
	@Qualifier("RedisDatabase")
	private IUserDao userDao;

	public User getUserByName(String username) {
		return userDao.getUserByName(username);
	}

	public void removeUserByName(String username) {
		userDao.removeUserByName(username);
	}

	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	Collection<User> getFollowed(User user) {
		return userDao.getFollowed(user);
	}
	Iterable<User> getFollowers(User user) {
		return userDao.getFollowers(user);
	}

	boolean toggleFollowing(User follower, User following) {
		return userDao.toggleFollowing(follower, following);
	}
}
