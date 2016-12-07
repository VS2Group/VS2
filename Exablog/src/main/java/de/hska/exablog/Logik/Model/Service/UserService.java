package de.hska.exablog.Logik.Model.Service;

import de.hska.exablog.Logik.Exception.*;
import de.hska.exablog.Logik.Model.Database.Dao.IUserDao;
import de.hska.exablog.Logik.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class UserService {

	@Autowired
	@Qualifier("RedisDatabase")
	private IUserDao userDao;

	public static String escapeHTML(String s) {
		StringBuilder out = new StringBuilder(Math.max(16, s.length()));
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
				out.append("&#");
				out.append((int) c);
				out.append(';');
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public User getUserByName(@NotNull String username) throws UserDoesNotExistException {
		return userDao.getUserByName(username);
	}

	public void removeUserByName(@NotNull String username) throws UserDoesNotExistException {
		if(getUserByName(username) == null) {
			throw new UserDoesNotExistException();
		}

		userDao.removeUserByName(username);
	}

	public void updateUser(@NotNull User user) throws UserDoesNotExistException {
		if(getUserByName(user.getUsername()) == null) {
			throw new UserDoesNotExistException();
		}

		userDao.updateUser(user);
	}

	public User insertUser(@NotNull User user) throws UserAlreadyExistsException, UsernameIllegalWhitespaceException,
			UsernameTooShortException, PasswordTooShortException {

		// Username normalisieren
		user.setUsername(user.getUsername().trim());

		if (user.getUsername().length() < 3) {
			throw new UsernameTooShortException();
		}

		// HTML/JavaScript ungefährlich machen
		user.setUsername(escapeHTML(user.getUsername()));

		// Schauen ob es noch Whitespaces gibt
		if (user.getUsername().matches(".*\\s.*")) {
			throw new UsernameIllegalWhitespaceException();
		}

		if (user.getPassword().length() < 3) {
			throw new PasswordTooShortException();
		}


		try {
			getUserByName(user.getUsername());
			throw new UserAlreadyExistsException();
		} catch (UserDoesNotExistException e) {
			return userDao.insertUser(user);
		}

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

	public ArrayList<User> searchFor(String username) {
		// TODO!!!
		return new ArrayList<>();
	}
}
