package de.hska.exablog.datenbank.MVC.Controller;


import de.hska.exablog.datenbank.MVC.Entity.User;
import de.hska.exablog.datenbank.MVC.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Angelo on 04.12.2016.
 */
//@Controller
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public User getUserByUsername(@PathVariable("username") String username){
		return userService.getUserByName(username);
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	public void removeUserByName(@PathVariable("username") String username) {
		userService.removeUserByName(username);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertStudent(@RequestBody User user){
		userService.insertUser(user);
	}
}
