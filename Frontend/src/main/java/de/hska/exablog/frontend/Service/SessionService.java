package de.hska.exablog.frontend.Service;

import de.hska.exablog.datenbank.MVC.Entity.Session;
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
public class SessionService {

	@Autowired
	private RestConfig restConfig;
	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		restTemplate = restConfig.getRestTemplate();
	}

	public User validateSession(String sessionId) {
		return restTemplate.getForObject(
				RestConfig.getUrl("/sessions/" + sessionId),
				User.class);
	}

	public Session registerSession(String sessionId, String username) {
		return restTemplate.postForObject(
				RestConfig.getUrl(("/sessions")),
				Session.getBuilder()
						.setSessionId(sessionId)
						.setUser(User.getBuilder()
								.setUsername(username)
								.build())
						.build(),
				Session.class);
	}

	public void removeSession(String sessionId) {
		restTemplate.delete(RestConfig.getUrl("/sessions/" + sessionId));
	}
}
