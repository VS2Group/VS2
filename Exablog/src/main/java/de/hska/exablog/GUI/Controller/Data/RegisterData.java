package de.hska.exablog.GUI.Controller.Data;


import de.hska.exablog.Logik.Model.Entity.User;

/**
 * Created by Angelo on 07.12.2016.
 */
public class RegisterData {
	private ErrorState errorState = ErrorState.NO_ERROR;
	private User user = User.getBuilder()
			.setImageUrl(RandomProfilePicture.getRandomFile())
			.build();
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
		ERROR_USERNAME_IN_USE,
		ERROR_USERNAME_ILLEGAL_WHITESPACE,
		ERROR_USERNAME_TOO_SHORT,
		ERROR_PASSWORD_TOO_SHORT;

		public String toString() {
			switch (this) {
				case NO_ERROR:
					return "";
				case ERROR_USERNAME_IN_USE:
					return "Fehler: Der Benutzername ist bereits in Verwendung.";
				case ERROR_USERNAME_ILLEGAL_WHITESPACE:
					return "Fehler: Der Benutzername enthält Leerzeichen.";
				case ERROR_USERNAME_TOO_SHORT:
					return "Fehler: Der Benutzername ist zu kurz. Mindestens 3 Zeichen.";
				case ERROR_PASSWORD_TOO_SHORT:
					return "Fehler: Das Passwort ist zu kurz. Mindestens 3 Zeichen.";
			}

			return "Unbekannter Fehler.";
		}
	}
}

