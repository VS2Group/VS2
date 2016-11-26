package de.hska.exablog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping(value = "signup")
public class SignUpController {
	
	//@Autowired
	//private RedisTemplate<String, String> redis;
			
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String showSignUp(Model model) {
		model.addAttribute("signUpData", new SignUpData());
		return "signup";
	}
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.POST)
	public String processSignUp(@ModelAttribute SignUpData signUpData, Model model) {
		model.addAttribute("signUpData", signUpData);
		
		/*try{
			UserFactory.createUser(redis, signUpData.getUsername(), signUpData.getPassword());	
			return "redirect:/signin";
		}
		catch(UserException e){
			signUpData.setUserException(e);
		}*/
			
		return "signup";
	}
	
}