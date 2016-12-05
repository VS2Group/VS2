package de.hska.exablog.datenbank.MVC.Controller;

import de.hska.exablog.datenbank.MVC.Entity.Post;
import de.hska.exablog.datenbank.MVC.Entity.User;
import de.hska.exablog.datenbank.MVC.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Angelo on 03.12.2016.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Post createPost(@RequestBody Post post) {
		return postService.createPost(post.getUser(), post.getContent());
	}

	@RequestMapping(value = "/{postid}", method = RequestMethod.DELETE)
	public void deletePost(@PathVariable("postid") long postID) {
		postService.deletePost(postID);
	}

}
