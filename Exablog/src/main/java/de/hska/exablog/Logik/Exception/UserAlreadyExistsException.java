package de.hska.exablog.Logik.Exception;

/**
 * Created by Angelo on 07.12.2016.
 */
public class UserAlreadyExistsException extends Exception {
	public UserAlreadyExistsException() {
		super("User existiert bereits!");
	}
}
