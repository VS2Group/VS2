package de.hska.exablog.Logik.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by Angelo on 03.12.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
	private long postID;
	private String content;
	private User user;
	private Date timestamp;

	private Post(long id, String content, User user, Date timestamp) {
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

	public void setPostID(long postID) {
		this.postID = postID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return user.getUsername();
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static class Builder {
		private long postID = -1;
		private String content = "";
		private User user = User.getBuilder().build();
		private Date timestamp = new Date();

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

		public Builder setUser(User user) {
			this.user = user;
			return this;
		}

		public Builder setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Post build() {
			return new Post(postID, content, user, timestamp);
		}
	}
}
