package de.hska.exablog.GUI.Controller.Stomp.Incoming;

import de.hska.exablog.GUI.Controller.Stomp.Outgoing.NewPostReply;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Angelo on 13.01.2017.
 */
public class NewPostRequest {
	private String sessionId;
	private String content;

	public NewPostRequest() {
	}

	public NewPostRequest(String sessionId, String content) {
		this.sessionId = sessionId;
		this.content = content;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getContent() {
		return content;
	}

}
