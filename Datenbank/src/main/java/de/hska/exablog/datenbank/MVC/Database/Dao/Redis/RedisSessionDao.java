package de.hska.exablog.datenbank.MVC.Database.Dao.Redis;

import de.hska.exablog.datenbank.Config.RedisConfig;
import de.hska.exablog.datenbank.MVC.Database.Dao.ISessionDao;
import de.hska.exablog.datenbank.MVC.Database.RedisDatabase;
import de.hska.exablog.datenbank.MVC.Entity.User;
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

	@Override
	public User validateSession(String sessionId) {
		String simpleLoginTokenKey = "session:" + sessionId;
		if (database.getCurrentLoginsOps().get(simpleLoginTokenKey) == null) {
			return null;
		}

		database.getStringRedisTemplate()
				.expire(simpleLoginTokenKey, RedisConfig.SESSION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
		// reset session timeout, since session still exists


		String loginKey = "login:" + user.getUsername();
		return database.getRegisteredLoginsOps()
				.get(loginKey, "password")
				.equals(user.getPassword());
	}
}
