package de.hska.exablog.GUI.Controller;

import de.hska.exablog.GUI.Controller.Data.RegisterData;
import de.hska.exablog.Logik.Exception.UserAlreadyExistsException;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Exception.UsernameIllegalWhitespaceException;
import de.hska.exablog.Logik.Exception.UsernameTooShortException;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Exception.PasswordTooShortException;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
	public String getRegister(@CookieValue(value = "oldSession", defaultValue = "") String oldSession,
							  @NotNull HttpSession session, @NotNull Model model) {
		if (sessionService.validateSession(session.getId()) != null) {    // User ist eingeloggt
			return "redirect:/timeline";
		}

		User oldUser = sessionService.validateSession(oldSession);
		if (oldUser != null) {
			sessionService.removeSession(oldSession);
			try {
				sessionService.registerSession(session.getId(), oldUser);
				return "redirect:/timeline";
			} catch (UserDoesNotExistException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("registerData", new RegisterData());
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postRegister(@CookieValue(value = "oldSession", defaultValue = "") String oldSession,
							   @NotNull @ModelAttribute RegisterData registerData, @NotNull HttpSession session, @NotNull Model model) {
		if (sessionService.validateSession(session.getId()) != null) {    // User ist noch eingeloggt
			return "redirect:/timeline";
		}

		if (!model.containsAttribute("registerData")) {
			model.addAttribute("registerData", new RegisterData());
		}

		if (registerData.isSubmitted()) {
			try {
				User registeredUser = userService.insertUser(registerData.getUser());
				sessionService.registerSession(session.getId(), registeredUser);
				oldSession = session.getId();
				return "redirect:/timeline";
			} catch (UserAlreadyExistsException e) {
				registerData.setErrorState(RegisterData.ErrorState.ERROR_USERNAME_IN_USE);
			} catch (UsernameIllegalWhitespaceException e) {
				registerData.setErrorState(RegisterData.ErrorState.ERROR_USERNAME_ILLEGAL_WHITESPACE);
			} catch (PasswordTooShortException e) {
				registerData.setErrorState(RegisterData.ErrorState.ERROR_PASSWORD_TOO_SHORT);
			} catch (UsernameTooShortException e) {
				registerData.setErrorState(RegisterData.ErrorState.ERROR_USERNAME_TOO_SHORT);
			} catch (UserDoesNotExistException e) {
				throw new RuntimeException("An unexpected error occurred", e);
			}
		}
		return "register";
	}


}
