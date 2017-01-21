package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Exception.SessionIsNotValid;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Database.Dao.ISessionDao;
import de.hska.exablog.Logik.Model.Database.RedisDatabase;
import de.hska.exablog.Logik.Model.Entity.Session;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Created by Angelo on 04.12.2016.
 */
@Repository
@Qualifier("RedisDatabase")
public class RedisSessionDao implements ISessionDao {

	@Autowired
	private RedisDatabase database;

	@Autowired
	private UserService userService;

	@Override
	public User validateSession(String sessionId) {
		try {
			User user = validateOnlySession(sessionId);
			if (user == null) {
				throw new SessionIsNotValid();
			}

			// reset session timeout
			registerSession(sessionId, user);
			return user;
		} catch (UserDoesNotExistException | SessionIsNotValid e) {
			return null;
		}
	}

	@Override
	public Session registerSession(String sessionId, User user) throws UserDoesNotExistException {
		String key = "session:" + sessionId;

		database.getSessionUserOps().set(key, user.getUsername(), RedisConfig.SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
		return Session.getBuilder()
				.setSessionId(sessionId)
				.setUser(user)
				.build();
	}

	@Override
	public void removeSession(String sessionId) {
		String key = "session:" + sessionId;

		database.getSessionUserOps().getOperations().delete(key);
	}

	@Override
	public User validateOnlySession(String sessionId) {
		String simpleLoginTokenKey = "session:" + sessionId;


		String username = database.getSessionUserOps().get(simpleLoginTokenKey);
		if (username == null) {
			return null;
		}

		try {
			User user = userService.getUserByName(username);
			return user;
		} catch (UserDoesNotExistException e) {
			return null;
		}
	}

}
