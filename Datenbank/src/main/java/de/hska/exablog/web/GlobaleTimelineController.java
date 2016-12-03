package de.hska.exablog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "timeline")
public class GlobaleTimelineController {
	
	//@Autowired
	//private RedisTemplate<String, String> redis;
	
	@RequestMapping(value = {"", "/"})
	public String showSignUp(Model model) {
		//model.addAttribute("postContainer", PostFactory.getGlobalTimeline(redis));
		return "exablogtimeline";
	}
}
