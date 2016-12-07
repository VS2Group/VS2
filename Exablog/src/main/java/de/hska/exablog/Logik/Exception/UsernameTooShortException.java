package de.hska.exablog.Logik.Exception;

/**
 * Created by Angelo on 07.12.2016.
 */
public class UsernameTooShortException extends Exception {
	public UsernameTooShortException() {
		super("The username is too short.");
	}
}
