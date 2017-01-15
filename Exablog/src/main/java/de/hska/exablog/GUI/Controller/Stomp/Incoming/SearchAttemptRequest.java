package de.hska.exablog.GUI.Controller.Stomp.Incoming;

/**
 * Created by Angelo on 13.01.2017.
 */
public class SearchAttemptRequest {
	private String searchString;

	public SearchAttemptRequest(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchString() {
		return searchString;
	}
}
