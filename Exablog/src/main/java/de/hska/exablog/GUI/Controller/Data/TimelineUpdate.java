package de.hska.exablog.GUI.Controller.Data;

import de.hska.exablog.Logik.Model.Entity.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Used by Stomp
 */
public class TimelineUpdate {
	private List<Post> newPosts = new ArrayList<>();

	public TimelineUpdate(Collection<Post> newPosts) {
		newPosts.addAll(newPosts);
	}

	public List<Post> getNewPosts() {
		return newPosts;
	}
}
