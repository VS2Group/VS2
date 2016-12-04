package de.hska.exablog.datenbank.Exceptions;

/**
 * Created by Angelo on 03.12.2016.
 */
public class DoesNotExistException extends RuntimeException {
	public DoesNotExistException() {
		super();
	}

	public DoesNotExistException(String message) {
		super(message);
	}
}