package de.hska.exablog.Logik.Model.Service;

import de.hska.exablog.Logik.Exception.AlreadyExistsException;
import de.hska.exablog.Logik.Exception.DoesNotExistException;
import de.hska.exablog.Logik.Model.Database.Dao.IUserDao;
import de.hska.exablog.Logik.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class UserService {

	@Autowired
	@Qualifier("RedisDatabase")
	private IUserDao userDao;

	public User getUserByName(@NotNull String username) {
		return userDao.getUserByName(username);
	}

	public void removeUserByName(@NotNull String username) throws DoesNotExistException {
		if(getUserByName(username) == null) {
			throw new DoesNotExistException("User existiert nicht!");
		}

		userDao.removeUserByName(username);
	}

	public void updateUser(@NotNull User user) throws DoesNotExistException {
		if(getUserByName(user.getUsername()) == null) {
			throw new DoesNotExistException("User existiert nicht!");
		}

		userDao.updateUser(user);
	}

	public User insertUser(@NotNull User user) throws AlreadyExistsException {
		if(getUserByName(user.getUsername()) != null) {
			throw new AlreadyExistsException("User existiert bereits!");
		}

		return userDao.insertUser(user);
	}

	Collection<User> getFollowed(@NotNull User user) {
		return userDao.getFollowed(user);
	}
	Iterable<User> getFollowers(@NotNull User user) {
		return userDao.getFollowers(user);
	}

	boolean toggleFollowing(@NotNull User follower, @NotNull User following) {
		return userDao.toggleFollowing(follower, following);
	}
}
