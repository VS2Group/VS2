package de.hska.exablog.GUI.Controller;

import de.hska.exablog.GUI.Controller.Data.PostData;
import de.hska.exablog.Logik.Model.Entity.Session;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Angelo on 07.12.2016.
 */
@Controller
@RequestMapping("/timeline")
public class TimelineController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private TimelineService timelineService;

	@RequestMapping(method = RequestMethod.GET)
	public String getTimeline(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist eingeloggt
			return "redirect:/login";
		}

		model.addAttribute("postData", new PostData());
		model.addAttribute("user", user);
		model.addAttribute("timeline", timelineService.getGlobalTimeline());

		return "timeline";
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String getTimeline(@PathVariable("username") String username, HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist eingeloggt
			return "redirect:/login";
		}

		model.addAttribute("postData", new PostData());
		model.addAttribute("user", user);
		model.addAttribute("timeline", timelineService.getPersonalTimeline(user));

		return "timeline";
	}
}
