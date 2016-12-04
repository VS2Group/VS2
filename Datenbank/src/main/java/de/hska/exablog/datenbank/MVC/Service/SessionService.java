package de.hska.exablog.datenbank.MVC.Service;

import de.hska.exablog.datenbank.MVC.Database.Dao.ISessionDao;
import de.hska.exablog.datenbank.MVC.Entity.User;
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
	private ISessionDao loginSessionDao;

	public User validateSession(String sessionId) {
		return loginSessionDao.validateSession(sessionId);
	}
}
