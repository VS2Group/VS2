package de.hska.exablog.GUI.Controller;

import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
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
@Controller
public class FollowController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{source}/follow/{username}/", method = RequestMethod.GET)
	public String getFollow(@PathVariable("source") String source, @PathVariable("username") String username, HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist nicht eingeloggt
			return "redirect:/login";
		}

		try {
			User toFollow = userService.getUserByName(username);
			userService.follow(user, toFollow);
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
		}

		return "redirect:" + source;
	}

	@RequestMapping(value = "{source}/unfollow/{username}/", method = RequestMethod.GET)
	public String getUnfollow(@PathVariable("source") String source, @PathVariable("username") String username, HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist nicht eingeloggt
			return "redirect:/login";
		}

		try {
			User toFollow = userService.getUserByName(username);
			userService.unfollow(user, toFollow);
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
		}

		return "redirect:" + source;
	}

	@RequestMapping(value = "/followings", method = RequestMethod.GET)
	public String getFollowings(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist nicht eingeloggt
			return "redirect:/login";
		}

		model.addAttribute("followings", userService.getFollowings(user));

		return "followings";
	}

	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	public String getFollowers(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist nicht eingeloggt
			return "redirect:/login";
		}

		model.addAttribute("followers", userService.getFollowers(user));

		return "followers";
	}
}
