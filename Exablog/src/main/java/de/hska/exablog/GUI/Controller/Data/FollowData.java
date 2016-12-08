package de.hska.exablog.GUI.Controller.Data;

/**
 * Created by Angelo on 08.12.2016.
 */
public class FollowData {
	private String username = "";
	private String follow = "";
	private String page = "/timeline";

	public FollowData() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
}
