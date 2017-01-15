package de.hska.exablog.Logik.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by Angelo on 03.12.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Comparable<User>{

	private UserService userService;

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String imageUrl;
	private String confirmPassword;

	User(String username, String password, String firstName, String lastName, String imageUrl, UserService userService) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.imageUrl = imageUrl;
		this.userService = userService;
	}

	static public Builder getBuilder() {
		return new Builder();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof User)) {
			return false;
		}

		User that = (User) obj;

		return this.username.equals(((User) obj).username);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		for (int i = 0; i < username.length(); i++) {
			hash = hash * 31 + username.charAt(i);
		}

		return hash;
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

	public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword;}

	public String getConfirmPassword() { return confirmPassword; }

	public boolean canFollow(User user) {
		return !this.equals(user) && !userService.isFollowing(this, user);
	}

	public boolean canUnfollow(User user) {
		return !this.equals(user) && userService.isFollowing(this, user);
	}

	@Override
	public int compareTo(User o) {
		return this.username.compareTo(o.username);
	}

	static public class Builder {
		private String username = "";
		private String firstName = "";
		private String lastName = "";
		private String password = "";
		private String imageUrl = "/image/user.png";
		private UserService userService;

		public Builder setUserService(UserService userService) {
			this.userService = userService;
			return this;
		}

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
					imageUrl,
					userService);
		}
	}
}
