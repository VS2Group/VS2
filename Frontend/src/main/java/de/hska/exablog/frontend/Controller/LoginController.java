package de.hska.exablog.frontend.Controller;


import de.hska.exablog.datenbank.MVC.Entity.User;
import de.hska.exablog.frontend.Service.SessionService;
import de.hska.exablog.frontend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Angelo on 03.12.2016.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@RequestMapping(method = RequestMethod.GET)
	public String getLogin(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());

		if (user != null) {    // User ist eingeloggt
			return "redirect:/timeline";
		}

		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postLogin(@ModelAttribute User user, HttpSession session, Model model) {

		User userBefore = sessionService.validateSession(session.getId());
		if (userBefore != null) {    // User war noch eingeloggt
			sessionService.removeSession(session.getId()); // User ausloggen
		}

		User foundUser = userService.getUserByName(user.getUsername());
		if (foundUser == null) {
			//TODO: Fehlermeldung: User nicht gefunden
			return "login";
		}

		if (foundUser.getPassword() != user.getPassword()) {
			//TODO: Fehlermeldung: Passwort falsch
			return "login";
		}

		//login
		sessionService.registerSession(session.getId(), user.getUsername());

		// TODO: Willkommensnachricht?
		return "timeline";
	}


}
