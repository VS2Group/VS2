package de.hska.exablog.GUI.Controller.Stomp.Outgoing;

import de.hska.exablog.Logik.Model.Entity.User;

import java.util.Collection;

/**
 * Created by Angelo on 13.01.2017.
 */
public class SearchAttemptReply extends Reply {
	private Collection<User> users;

	public SearchAttemptReply(RequestState requestState, String message, Collection<User> users) {
		super(requestState, message);
		this.users = users;
	}

	public Collection<User> getUsers() {
		return users;
	}
}
