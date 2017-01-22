package de.hska.exablog.GUI.Controller;

import com.sun.istack.internal.Nullable;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by Angelo on 08.12.2016.
 */
@Controller
public class FollowController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private UserService userService;

	@NotNull
	private String getFollowRedirect(@Nullable String sourceOrNull) {
		if (sourceOrNull != null) {
			while (sourceOrNull.startsWith("/")) {
				sourceOrNull = sourceOrNull.substring(1, sourceOrNull.length());
			}

			return "redirect:/" + sourceOrNull;
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = {"/follow/{username}/", "/follow/{username}"}, method = RequestMethod.GET)
	public String follow(@RequestParam(value = "source", required = false) String sourceOrNull, @PathVariable("username") String username, HttpSession session) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist eingeloggt
			return "redirect:/login";
		}

		try {
			User toFollow = userService.getUserByName(username);
			userService.follow(user, toFollow);
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
		}


		return getFollowRedirect(sourceOrNull);
	}

	@RequestMapping(value = {"/unfollow/{username}/", "/unfollow/{username}"}, method = RequestMethod.GET)
	public String unfollow(@RequestParam(value = "source", required = false) String sourceOrNull, @PathVariable("username") String username, HttpSession session) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist eingeloggt
			return "redirect:/login";
		}

		try {
			User toFollow = userService.getUserByName(username);
			userService.unfollow(user, toFollow);
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
		}


		return getFollowRedirect(sourceOrNull);
	}

	@RequestMapping(value = "/followings", method = RequestMethod.GET)
	public String getFollowings(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist nicht eingeloggt
			return "redirect:/login";
		}


		Collection<User> users = userService.getFollowings(user);
		model.addAttribute("_session", session);
		model.addAttribute("results", users);
		model.addAttribute("user", user);
		return "followings";
	}

	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	public String getFollowers(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist nicht eingeloggt
			return "redirect:/login";
		}

		Collection<User> users = userService.getFollowers(user);
		model.addAttribute("_session", session);
		model.addAttribute("results", users);
		model.addAttribute("user", user);

		return "followers";
	}
}
