package de.hska.exablog.frontend.Service;

import de.hska.exablog.datenbank.MVC.Entity.Post;
import de.hska.exablog.frontend.Config.RestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class PostService {

	@Autowired
	private RestConfig restConfig;
	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		restTemplate = restConfig.getRestTemplate();
	}

	public Post createPost(Post post) {
		return restTemplate.postForObject(
				RestConfig.getUrl("/posts"),
				new HttpEntity<>(post),
				Post.class);
	}

	public void deletePost(long postID) {
		restTemplate.delete(RestConfig.getUrl("/posts/" + postID));
	}

}
