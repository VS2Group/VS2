package de.hska.exablog.GUI.Controller.Stomp.Outgoing;

import de.hska.exablog.Logik.Model.Entity.Post;

import java.util.Collection;

/**
 * Created by Angelo on 13.01.2017.
 */
public class TimelineUpdate {

	private Collection<Post> posts;

	public TimelineUpdate(Collection<Post> posts) {
		this.posts = posts;
	}

	public Collection<Post> getPosts() {
		return posts;
	}
}
