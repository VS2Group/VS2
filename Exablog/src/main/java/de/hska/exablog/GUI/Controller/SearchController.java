package de.hska.exablog.GUI.Controller;

import de.hska.exablog.GUI.Controller.Data.PostData;
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
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Angelo on 07.12.2016.
 */
@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@RequestMapping(method = RequestMethod.GET)
	public String getUsers(@ModelAttribute("searchterm") String searchterm, HttpSession session, Model model) {

		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist noch eingeloggt
			return "redirect:/login";
		}

		if (searchterm == null) {
			model.addAttribute("results", new ArrayList<User>());
			return "searchresults";
		}

		Collection<User> users = userService.searchForUsers(searchterm);
		model.addAttribute("_session", session);
		model.addAttribute("postData", new PostData());
		model.addAttribute("results", users);
		model.addAttribute("user", user);
		model.addAttribute("searchterm", searchterm);

		return "searchresults";
	}
}
