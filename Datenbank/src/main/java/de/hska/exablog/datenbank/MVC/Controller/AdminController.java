package de.hska.exablog.datenbank.MVC.Controller;

import de.hska.exablog.datenbank.MVC.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Angelo on 05.12.2016.
 */
@Controller
public class AdminController {

	@Autowired
	private UserController userController;

	@RequestMapping(value = "/register")
	public String postRegister(@ModelAttribute User user, Model model) {
		if (user.getUsername().equals("")) {
			return "register";
		}

		User registeredUser = userController.insertUser(user);
		if (registeredUser != null) {
			model.addAttribute("registeredUsernameMessage", String.format("User %s wurde registriert!", registeredUser.getUsername()));
		}

		return "register";
	}
}
