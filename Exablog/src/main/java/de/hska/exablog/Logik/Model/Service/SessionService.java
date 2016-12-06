package de.hska.exablog.Logik.Model.Service;

import de.hska.exablog.Logik.Model.Database.Dao.ISessionDao;
import de.hska.exablog.Logik.Model.Entity.Session;
import de.hska.exablog.Logik.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class SessionService {

	@Autowired
	@Qualifier("RedisDatabase")
	private ISessionDao sessionDao;

	public User validateSession(String sessionId) {
		return sessionDao.validateSession(sessionId);
	}

	public Session registerSession(String sessionId, String username) {
		return sessionDao.registerSession(sessionId, username);
	}

	public void removeSession(String sessionId) {
		sessionDao.removeSession(sessionId);
	}
}
