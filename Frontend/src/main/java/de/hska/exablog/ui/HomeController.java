package de.hska.exablog.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "exablogtimeline")
public class HomeController {
	
	/*@Autowired
	private RedisTemplate<String, String> redis;
	*/
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String showSignUp(Model model, HttpServletRequest req) {
		
		//User user = AccessRestriction.getUser(redis, req);
		
		//model.addAttribute("post", new Post());
		//model.addAttribute("postContainer", PostFactory.getUserTimeline(redis, user));
		return "exablogtimeline";
	}
	
	/*
	@RequestMapping(value = {"", "/"}, method=RequestMethod.POST)
	public String createNewPost(
			@ModelAttribute Post post, 
			Model model, 
			HttpServletRequest req,
			HttpServletResponse res) {
		
		User user = AccessRestriction.getUser(redis, req);
		PostFactory.createPost(redis, user, post.getText());
		
		System.out.println(post.getText());		
		
		model.addAttribute("post", post);
		model.addAttribute("postContainer", PostFactory.getUserTimeline(redis, user));
		return "home";
	}*/
}