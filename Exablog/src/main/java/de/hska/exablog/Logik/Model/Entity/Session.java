package de.hska.exablog.Logik.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Angelo on 04.12.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {
	private String sessionId = "";
	private User user;
	private long timestamp;

	public Session() {
	}

	public Session(String sessionId, User user, long timestamp) {
		this.sessionId = sessionId;
		this.user = user;
		this.timestamp = timestamp;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public String getSessionId() {
		return sessionId;
	}

	public User getUser() {
		return user;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public static class Builder {
		private String sessionId;
		private User user;
		private long timestamp;

		public Builder() {

		}

		public Builder setSessionId(String sessionId) {
			this.sessionId = sessionId;
			return this;
		}

		public Builder setUser(User user) {
			this.user = user;
			return this;
		}

		public Builder setTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Session build() {
			return new Session(sessionId, user, timestamp);
		}
	}
}
