package de.vs2group.Controller;

import de.vs2group.Entity.User;
import de.vs2group.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "/{id}", 	method = RequestMethod.GET)
	public User getUserById(@PathVariable("id") int id) {
		return userService.getUserById(id);
	}

	@RequestMapping(value = "/{id}", 	method = RequestMethod.DELETE)
	public void deleteUserById(@PathVariable("id") int id) {
		userService.deleteUserById(id);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insertUser(@RequestBody User user) {
		userService.insertUser(user);
	}
}
