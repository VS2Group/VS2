package de.hska.exablog.Logik.Exception;

import java.util.Objects;

/**
 * Created by Angelo on 03.12.2016.
 */
public class UserDoesNotExistException extends Exception {
	public UserDoesNotExistException() {
		this("", false);
	}
	public UserDoesNotExistException(String username, boolean onDelete) {
		super ((Objects.equals(username, "") ? "Some user" : ("User " + username)) + " was being " + (onDelete ? "deleted" : "accessed") + ", but could not be found.");
	}
}