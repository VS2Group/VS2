package de.hska.exablog.GUI.Controller.Stomp.Outgoing;

import de.hska.exablog.Logik.Model.Entity.Post;

/**
 * Created by Angelo on 13.01.2017.
 */
public class NewPostReply extends Reply {
	private Post post;


	public NewPostReply(Post post) {
		super(RequestState.SUCCEEDED, "");
		this.post = post;
	}

	public NewPostReply(RequestState requestState, String message) {
		super(requestState, message);
	}

	public Post getPost() {
		return post;
	}
}
