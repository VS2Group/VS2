package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Entity.Session;
import de.hska.exablog.Logik.Model.Entity.User;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface ISessionDao {
	User validateSession(String sessionId);

	Session registerSession(String sessionId, User user) throws UserDoesNotExistException;

	void removeSession(String sessionId);

	User validateOnlySession(String sessionId);
}
