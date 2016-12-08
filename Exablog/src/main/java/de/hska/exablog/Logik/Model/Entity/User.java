package de.hska.exablog.Logik.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Angelo on 03.12.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String imageUrl;

	User(String username, String password, String firstName, String lastName, String imageUrl) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.imageUrl = imageUrl;
	}

	static public Builder getBuilder() {
		return new Builder();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	static public class Builder {
		private String username = "";
		private String firstName = "";
		private String lastName = "";
		private String password = "";
		private String imageUrl = "/image/user.png";

		public Builder setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public User build() {
			return new User(
					username,
					password,
					firstName,
					lastName,
					imageUrl);
		}
	}
}
