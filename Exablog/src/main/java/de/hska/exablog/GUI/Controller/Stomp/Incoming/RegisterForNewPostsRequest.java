package de.hska.exablog.GUI.Controller.Stomp.Incoming;

/**
 * Created by Angelo on 19.01.2017.
 */
public class RegisterForNewPostsRequest {
	private String sessionId;

	public RegisterForNewPostsRequest() {
	}

	public RegisterForNewPostsRequest(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}
}
