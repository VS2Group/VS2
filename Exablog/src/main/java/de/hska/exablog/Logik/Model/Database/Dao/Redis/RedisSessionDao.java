package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
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
		String simpleLoginTokenKey = "session:" + sessionId;


		String username = database.getSessionUserOps().get(simpleLoginTokenKey);
		if (username == null) {
			return null;
		}

		try {
			// reset session timeout
			User user = userService.getUserByName(username);
			registerSession(sessionId, user);
			return user;
		} catch (UserDoesNotExistException e) {
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
}
