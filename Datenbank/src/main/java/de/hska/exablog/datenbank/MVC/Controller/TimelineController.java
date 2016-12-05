package de.hska.exablog.datenbank.MVC.Controller;

import de.hska.exablog.datenbank.MVC.Entity.Timeline;
import de.hska.exablog.datenbank.MVC.Service.TimelineService;
import de.hska.exablog.datenbank.MVC.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Angelo on 03.12.2016.
 */
@RestController
@RequestMapping("/timelines")
public class TimelineController {

	@Autowired
	private TimelineService timelineService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/global", method = RequestMethod.GET)
	public Timeline getGlobalTimeline() {
		return timelineService.getGlobalTimeline();
	}

	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public Timeline getPersonalTimeline(@PathVariable("username") String username) {
		return timelineService.getPersonalTimeline(userService.getUserByName(username));
	}

}
