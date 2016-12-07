package de.hska.exablog.GUI.Controller;


import de.hska.exablog.GUI.Controller.Data.LoginData;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
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
	public String getLogin(@NotNull HttpSession session, @NotNull Model model) {

		if (sessionService.validateSession(session.getId()) != null) {    // User ist eingeloggt
			return "redirect:/timeline";
		}

		model.addAttribute("loginData", new LoginData());
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postLogin(@NotNull @ModelAttribute LoginData loginData, @NotNull HttpSession session, @NotNull Model model) {

		User userBefore = sessionService.validateSession(session.getId());
		if (userBefore != null) {    // User war noch eingeloggt
			sessionService.removeSession(session.getId()); // User ausloggen
		}

		if (loginData.isSubmitted()) {

			try {
				User foundUser = userService.getUserByName(loginData.getUser().getUsername());

				if (!foundUser.getPassword().equals(loginData.getUser().getPassword())) {
					loginData.setErrorState(LoginData.ErrorState.ERROR_WRONG_PASSWORD);
					return "login";
				}

				loginData.setErrorState(LoginData.ErrorState.NO_ERROR);

				//login
				sessionService.registerSession(session.getId(), loginData.getUser().getUsername());

			} catch (UserDoesNotExistException e) {
				loginData.setErrorState(LoginData.ErrorState.ERROR_USER_NOT_FOUND);
				return "login";
			}


		} else {
			model.addAttribute("loginData", new LoginData());
		}

		return "redirect:/timeline";
	}


}
