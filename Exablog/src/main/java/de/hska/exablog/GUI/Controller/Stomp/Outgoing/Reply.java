package de.hska.exablog.GUI.Controller.Stomp.Outgoing;

/**
 * Created by Angelo on 13.01.2017.
 */
public class Reply {
	protected RequestState requestState;
	protected String message;

	public Reply(RequestState requestState, String message) {
		this.requestState = requestState;
		this.message = message;
	}

	public RequestState getRequestState() {
		return requestState;
	}

	public String getMessage() {
		return message;
	}

	public enum RequestState {
		SUCCEEDED,
		FAILED
	}
}
