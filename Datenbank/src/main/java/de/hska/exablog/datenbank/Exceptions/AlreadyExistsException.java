package de.hska.exablog.datenbank.Exceptions;

/**
 * Created by Angelo on 03.12.2016.
 */
public class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException() {
		super();
	}

	public AlreadyExistsException(String message) {
		super(message);
	}
}