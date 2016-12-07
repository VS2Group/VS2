package de.hska.exablog.Logik.Exception;

/**
 * Created by Angelo on 03.12.2016.
 */
public class UserDoesNotExistException extends Exception {
	public UserDoesNotExistException() {
		super("User existiert nicht!");
	}
}