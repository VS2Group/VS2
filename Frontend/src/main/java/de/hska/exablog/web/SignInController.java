package de.hska.exablog.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "signin")
public class SignInController {
	
	//@Autowired
	//private RedisTemplate<String, String> redis;

	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String showSignIn(Model model) {
		//model.addAttribute("signInData", new SignInData());
		return "signin";
	}
	
	@RequestMapping(value = "/signup", method=RequestMethod.GET)
	public String showSignUp(Model model) {
		//model.addAttribute("signInData", new SignInData());
		return "signup";
	}
	
	/*
	@RequestMapping(value = {"", "/"}, method=RequestMethod.POST)
	public String processSignIn(
			@ModelAttribute SignInData signInData, 
			Model model, 
			HttpServletResponse response) {
		
		model.addAttribute("signInData", signInData);		
		
		try{
			User user = UserFactory.getUserByLogin(redis, signInData.getUsername(), signInData.getPassword());
			AccessRestriction.setSessionCookies(user, response);
			return "redirect:/home";			
		}catch(UserException e){
			signInData.setUserException(e);
		}
		return "signin";
		
	}
	*/
}

