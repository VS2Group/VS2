package de.hska.exablog.datenbank.MVC.Controller;

import de.hska.exablog.datenbank.MVC.Entity.Session;
import de.hska.exablog.datenbank.MVC.Entity.User;
import de.hska.exablog.datenbank.MVC.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Angelo on 04.12.2016.
 */

@RestController
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@RequestMapping(value="/{sessionid}", method = RequestMethod.GET)
	public User validateSession(@PathVariable("sessionid") String sessionId) {
		return sessionService.validateSession(sessionId);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Session registerSession(@RequestBody Session session) {
		return sessionService.registerSession(session.getSessionId(), session.getUser().getUsername());
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeSession(@RequestBody Session session) {
		sessionService.removeSession(session.getSessionId());
	}

}
