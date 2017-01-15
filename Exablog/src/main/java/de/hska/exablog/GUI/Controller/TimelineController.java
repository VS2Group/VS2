package de.hska.exablog.GUI.Controller;

import de.hska.exablog.GUI.Controller.Data.PostData;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
import de.hska.exablog.Logik.Model.Entity.Session;
import de.hska.exablog.Logik.Model.Entity.Timeline;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.TimelineService;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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
	@Autowired
	private UserService userService;
	@Autowired
	private FollowController followController;

	@RequestMapping(method = RequestMethod.GET)
	public String getGlobalTimeline(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User ist eingeloggt
			return "redirect:/login";
		}

		model.addAttribute("_session", session);
		model.addAttribute("timelinetype", "global");
		model.addAttribute("postData", new PostData());
		model.addAttribute("user", user);
		Timeline globalTimeline = timelineService.getGlobalTimeline(0);
		model.addAttribute("timeline", globalTimeline);
		return "timeline";
	}

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public String getPersonalTimeline(HttpSession session, Model model) {
		User thisUser = sessionService.validateSession(session.getId());
		if (thisUser == null) {    // User ist eingeloggt
			return "redirect:/login";
		}

		model.addAttribute("_session", session);
		model.addAttribute("timelinetype", "private");
		model.addAttribute("postData", new PostData());
		model.addAttribute("user", thisUser);
		Timeline personalTimeline = timelineService.getPersonalTimeline(thisUser, 0);
		model.addAttribute("timeline", personalTimeline);

		return "timeline";
	}


}
