package de.hska.exablog.GUI.Controller.Data;

import de.hska.exablog.Logik.Model.Entity.Post;

/**
 * Created by Angelo on 07.12.2016.
 */
public class PostData {
	private Post post = Post.getBuilder().build();
	private String comingfrom = "timeline";

	public PostData() {
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getComingfrom() {
		return comingfrom;
	}

	public void setComingfrom(String comingfrom) {
		this.comingfrom = comingfrom;
	}
}
