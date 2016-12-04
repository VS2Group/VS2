package de.hska.exablog.datenbank.MVC.Database.Dao.Redis;

import de.hska.exablog.datenbank.Config.RedisConfig;
import de.hska.exablog.datenbank.MVC.Database.Dao.ISessionDao;
import de.hska.exablog.datenbank.MVC.Database.RedisDatabase;
import de.hska.exablog.datenbank.MVC.Entity.User;
import de.hska.exablog.datenbank.MVC.Service.UserService;
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

		// reset session timeout
		registerSession(sessionId, username);

		return userService.getUserByName(username);
	}

	@Override
	public void registerSession(String sessionId, String username) {
		String key = "session:" + sessionId;

		database.getSessionUserOps().set(key, username, RedisConfig.SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
	}
}
