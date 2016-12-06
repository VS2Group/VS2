package de.hska.exablog.GUI.Controller;

import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Angelo on 06.12.2016.
 */
@Controller
@RequestMapping("/login")
public class RegisterController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register")
	public String postRegister(@ModelAttribute User user, Model model) {
		if (user.getUsername().equals("")) {
			return "register";
		}

		User registeredUser = userService.insertUser(user);
		if (registeredUser != null) {
			model.addAttribute("registeredUsernameMessage", String.format("User %s wurde registriert!", registeredUser.getUsername()));
		}

		return "register";
	}
}
