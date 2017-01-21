package de.hska.exablog.GUI.Controller.Stomp.Outgoing;

import de.hska.exablog.GUI.Controller.Stomp.Outgoing.Reply;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.messaging.core.MessagePostProcessor;

/**
 * Created by Angelo on 19.01.2017.
 */
public class RegisterForNewPostsReply {
	private RequestState state;
	private String message;

	public RegisterForNewPostsReply(RequestState failed, String message) {

		state = failed;
		this.message = message;
	}

	public RegisterForNewPostsReply() {
		this(RequestState.SUCCEEDED, "");
	}

	public RequestState getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	public enum RequestState {
		SUCCEEDED,
		FAILED
	}
}
