package de.hska.exablog.Logik.Exception;

/**
 * Created by Angelo on 07.12.2016.
 */
public class PasswordTooShortException extends Exception {

	/**
	 * Constructs a new throwable with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 * <p>
	 * <p>The {@link #fillInStackTrace()} method is called to initialize
	 * the stack trace data in the newly created throwable.
	 */
	public PasswordTooShortException() {
		super("The password is too short.");
	}
}
