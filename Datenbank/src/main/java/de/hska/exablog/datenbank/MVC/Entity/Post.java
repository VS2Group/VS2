package de.hska.exablog.datenbank.MVC.Entity;

/**
 * Created by Angelo on 03.12.2016.
 */
public class Post {
	private long postID;
	private String content;
	private User user;
	private long timestamp;

	public Post(long id, String content, User user, long timestamp) {
		this.postID = id;
		this.user = user;
		this.timestamp = timestamp;
		this.content = content;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public long getPostID() {
		return postID;
	}

	public String getContent() {
		return content;
	}

	public String getUsername() {
		return user.getUsername();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public User getUser() {
		return user;
	}

	public static class Builder {
		private long postID;
		private String content;
		private User user;
		private long timestamp;

		public Builder() {
		}

		public Builder setPostID(long postID) {
			this.postID = postID;
			return this;
		}

		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public Builder setUsername(String username) {
			this.user = user;
			return this;
		}

		public Builder setTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Post build() {
			return new Post(postID, content, user, timestamp);
		}
	}
}
