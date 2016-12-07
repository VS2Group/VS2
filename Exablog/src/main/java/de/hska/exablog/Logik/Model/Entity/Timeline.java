package de.hska.exablog.Logik.Model.Entity;

/**
 * Created by Angelo on 03.12.2016.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Timeline {

	private ArrayList<Post> posts = new ArrayList<>();

	public Timeline() {
	}

	public Timeline(Collection<Post> posts) {
		this.posts.addAll(posts);
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

}
