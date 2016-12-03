package de.hska.exablog.datenbank.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "search")
public class SearchController {
	
	//@Autowired
	//private RedisTemplate<String, String> redis;
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String showEmptySearch(Model model) {
		//model.addAttribute("userContainer", new User[]{});
		model.addAttribute("searchTerm", "");
		return "suchenutzer";
	}	
	/*
	@RequestMapping(value = {"{searchTerm}", "/{searchTerm}"}, method=RequestMethod.GET)
	public String doSearch(Model model, @PathVariable String searchTerm) {
		model.addAttribute("userContainer", UserFactory.searchUser(redis, searchTerm));
		model.addAttribute("searchTerm", searchTerm);
		return "suchenutzer";
	}	
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.POST)
	public String redirect(Model model, @RequestParam("search") String searchTerm) {
		model.addAttribute("userContainer", new User[]{});
		model.addAttribute("searchTerm", "");
		return "redirect:/search/" + searchTerm;
	}	
	*/
}

