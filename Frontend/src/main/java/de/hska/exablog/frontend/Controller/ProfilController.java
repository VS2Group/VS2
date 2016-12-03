package de.hska.exablog.frontend.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "profil")
public class ProfilController {
	
	//@Autowired
	//private RedisTemplate<String, String> redis;
	
	@RequestMapping(value = {"{username}", "/{username}"}, method=RequestMethod.GET)
	public String showUser(
			Model model, 
			HttpServletRequest req,
			@PathVariable String username) {
		/*
		User ownUser = AccessRestriction.getUser(redis, req);
		User user = UserFactory.getUserByName(redis, username);
		Post[] posts = new Post[0];
		
		if (user != null){
			posts = PostFactory.getUserPosts(redis, user);
		}
		
		model.addAttribute("user", user);
		model.addAttribute("following", ownUser.amIFollowing(user));
		model.addAttribute("postContainer", posts);
		*/
		return "profil";
	}
	/*
	@RequestMapping(value = {"{username}", "/{username}"}, method=RequestMethod.POST)
	public String saveUser(
			Model model, 
			HttpServletRequest req,
			@PathVariable String username,
			@RequestParam(required = false, defaultValue = "false") boolean follow) {
		
		User ownUser = AccessRestriction.getUser(redis, req);
		User user = UserFactory.getUserByName(redis, username);		
		Post[] posts = new Post[0];		
		
		// nicht sich selber followen
		if (!ownUser.getName().equals(user.getName())){
			if (follow){ ownUser.followUser(redis, user); }
			else { ownUser.unfollowUser(redis, user); }			
		}
		
		if (user != null){
			posts = PostFactory.getUserPosts(redis, user);
		}
		
		model.addAttribute("user", user);
		model.addAttribute("following", ownUser.amIFollowing(user));
		model.addAttribute("postContainer", posts);
		
		return "redirect:/profil/" + username;
	}
	*/
}