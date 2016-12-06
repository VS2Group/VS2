package de.hska.exablog.GUI.Controller;

import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Angelo on 06.12.2016.
 */
@Controller
public class RegisterController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public String postRegister(@ModelAttribute User user, Model model) {
		if (user.getUsername().equals("")) {
			model.addAttribute("userRegister",new User());
			return "registrieren";
		}

		User registeredUser = userService.insertUser(user);
		if (registeredUser != null) {
			model.addAttribute("registeredUsernameMessage", String.format("User %s wurde registriert!", registeredUser.getUsername()));
		}

		return "registrieren";
	}
}
