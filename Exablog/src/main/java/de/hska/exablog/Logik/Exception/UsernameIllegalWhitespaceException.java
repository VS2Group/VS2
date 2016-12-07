package de.hska.exablog.Logik.Exception;

/**
 * Created by Angelo on 07.12.2016.
 */
public class UsernameIllegalWhitespaceException extends Exception {
	public UsernameIllegalWhitespaceException() {
		super("No whitespaces in username allowed");
	}
}
