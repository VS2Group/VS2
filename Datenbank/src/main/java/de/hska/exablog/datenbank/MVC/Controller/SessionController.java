package de.hska.exablog.datenbank.MVC.Controller;

import de.hska.exablog.datenbank.MVC.Entity.User;
import de.hska.exablog.datenbank.MVC.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value="/{sessionid}/{username}", method = RequestMethod.GET)
	public void registerSession(@PathVariable("sessionid") String sessionId, @PathVariable("username") String username) {
		sessionService.registerSession(sessionId, username);
	}
}
