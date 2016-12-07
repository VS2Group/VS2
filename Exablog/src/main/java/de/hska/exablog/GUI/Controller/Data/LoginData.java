package de.hska.exablog.GUI.Controller.Data;

import de.hska.exablog.Logik.Model.Entity.User;

/**
 * Created by Angelo on 07.12.2016.
 */
public class LoginData {
	private ErrorState errorState = ErrorState.NO_ERROR;
	private User user = User.getBuilder().build();
	private boolean submitted = false;

	public ErrorState getErrorState() {
		return errorState;
	}

	public void setErrorState(ErrorState errorState) {
		this.errorState = errorState;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = true;
	}

	public enum ErrorState {
		NO_ERROR,
		ERROR_USER_NOT_FOUND,
		ERROR_WRONG_PASSWORD;

		public String toString() {
			switch (this) {
				case NO_ERROR:
					return "";
				case ERROR_USER_NOT_FOUND:
					return "Fehler: Benutzer existiert nicht.";
				case ERROR_WRONG_PASSWORD:
					return "Fehler: Passwort falsch.";
			}

			return "Unbekannter Fehler.";
		}
	}
}
