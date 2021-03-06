package de.hska.exablog.GUI.Controller;

import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Angelo on 05.12.2016.
 */
@Controller
@RequestMapping(value = {"/", ""})
public class HomeController {

	@Autowired
	private SessionService sessionService;

	@RequestMapping(method = RequestMethod.GET)
	public String getHomePage(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());

		if (user != null) {    // User ist eingeloggt
			return "redirect:/timeline";
		}

		// User ist noch nicht eingeloggt
		return "redirect:/login";
	}

}
