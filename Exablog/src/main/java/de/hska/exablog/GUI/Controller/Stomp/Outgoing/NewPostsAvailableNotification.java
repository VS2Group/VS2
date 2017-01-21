package de.hska.exablog.GUI.Controller.Stomp.Outgoing;

/**
 * Created by Angelo on 20.01.2017.
 */
public class NewPostsAvailableNotification extends Reply {

	public NewPostsAvailableNotification(RequestState requestState, String message) {
		super(requestState, message);
	}

	public NewPostsAvailableNotification() {
		super(RequestState.SUCCEEDED, "");
	}
}
