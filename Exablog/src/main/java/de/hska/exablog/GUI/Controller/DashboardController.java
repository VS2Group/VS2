package de.hska.exablog.GUI.Controller;

import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Angelo on 08.12.2016.
 */
@RequestMapping("/dashboard")
@Controller
public class DashboardController {
	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;


	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String getDashboard(@PathVariable("username") String username, HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist noch eingeloggt
			return "redirect:/login";
		}

		model.addAttribute("user", user);

		return "dashboard";
	}
}
