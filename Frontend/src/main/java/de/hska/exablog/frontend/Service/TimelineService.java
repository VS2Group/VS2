package de.hska.exablog.frontend.Service;

import de.hska.exablog.datenbank.MVC.Entity.Timeline;
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
public class TimelineService {

	@Autowired
	private RestConfig restConfig;
	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		restTemplate = restConfig.getRestTemplate();
	}

	public Timeline getGlobalTimeline() {
		return restTemplate.getForObject(RestConfig.getUrl("/timelines/global"), Timeline.class);
	}

	public Timeline getPersonalTimeline(User user) {
		return restTemplate.getForObject(RestConfig.getUrl("/timelines/users/" + user.getUsername()),
				Timeline.class);
	}
}
