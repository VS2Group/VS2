package de.hska.exablog.GUI.Controller.Data;

import de.hska.exablog.Logik.Model.Entity.Post;

/**
 * Created by Angelo on 07.12.2016.
 */
public class PostData {
	private Post post = Post.getBuilder().build();
	private String page = "/timeline";

	public PostData() {
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}
