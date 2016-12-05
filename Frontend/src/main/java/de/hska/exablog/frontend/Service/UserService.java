package de.hska.exablog.frontend.Service;

import de.hska.exablog.datenbank.MVC.Entity.User;
import de.hska.exablog.frontend.Config.RestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class UserService {

	@Autowired
	private RestConfig restConfig;
	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		restTemplate = restConfig.getRestTemplate();
	}

	public User getUserByName(String username) {
		return restTemplate.getForObject(RestConfig.getUrl("/users/" + username), User.class);
	}

	public void removeUserByName(String username) {
		restTemplate.delete(RestConfig.getUrl("/users/" + username));
	}

	public void updateUser(User user) {
		restTemplate.put(RestConfig.getUrl("/users"), user);
	}

	public User insertUser(User user) {
		return restTemplate.postForObject(RestConfig.getUrl("/users"), user, User.class);
	}

}
