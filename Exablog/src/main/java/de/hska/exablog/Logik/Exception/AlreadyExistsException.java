package de.hska.exablog.Logik.Exception;

/**
 * Created by Angelo on 03.12.2016.
 */
public class AlreadyExistsException extends Exception {
	public AlreadyExistsException() {
		super();
	}

	public AlreadyExistsException(String message) {
		super(message);
	}
}