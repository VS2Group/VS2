package de.hska.exablog.GUI.Controller;

import de.hska.exablog.GUI.Controller.Data.FollowData;
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

/**
 * Created by Angelo on 08.12.2016.
 */
@Controller
public class FollowController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public String postFollow(@ModelAttribute FollowData followData, HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist nicht eingeloggt
			return "redirect:/login";
		}

		if (model.containsAttribute("followData")) {
			try {
				User toFollow = userService.getUserByName(followData.getUsername());

				if (followData.getFollow().equals("follow")) {
					userService.follow(user, toFollow);
				} else if (followData.getFollow().equals("unfollow")) {
					userService.unfollow(user, toFollow);
				}

			} catch (UserDoesNotExistException e) {
				e.printStackTrace();
			}

			return "redirect:" + followData.getPage();
		}

		return "redirect:/timeline";
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
