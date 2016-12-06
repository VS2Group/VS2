package de.hska.exablog.Logik.Exception;

/**
 * Created by Angelo on 03.12.2016.
 */
public class DoesNotExistException extends Exception {
	public DoesNotExistException() {
		super();
	}

	public DoesNotExistException(String message) {
		super(message);
	}
}