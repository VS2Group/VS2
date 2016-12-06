package de.hska.exablog.GUI.Controller;

import de.hska.exablog.Logik.Exception.AlreadyExistsException;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

/**
 * Created by Angelo on 06.12.2016.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;

	@RequestMapping(method = RequestMethod.GET)
	public String getRegister(@NotNull HttpSession session, @NotNull Model model) {
		if (sessionService.validateSession(session.getId()) != null) {    // User ist eingeloggt
			return "redirect:/timeline";
		}
		model.addAttribute("user",new User());
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postRegister(@NotNull @ModelAttribute User user, @NotNull HttpSession session, @NotNull Model model) {

		if (sessionService.validateSession(session.getId()) != null) {    // User ist eingeloggt
			return "redirect:/timeline";
		}

		if (registerCredentialsSet(user)) {
			try {
				User registeredUser = userService.insertUser(user);
				if (registeredUser != null) {
					model.addAttribute("registeredUsernameMessage", String.format("User %s wurde registriert!", registeredUser.getUsername()));
				}
			} catch (AlreadyExistsException e) {
				model.addAttribute("registeredUsernameMessage", String.format("Fehler: User %s existiert bereits!", user.getUsername()));
			}
		} else {
			model.addAttribute("user", new User());
		}

		return "register";
	}

	private boolean registerCredentialsSet(@NotNull User user) {
		return user.getPassword() != null && !user.getPassword().equals("")
				&& user.getUsername() != null && !user.getUsername().equals("");
	}

}
