package de.hska.exablog.datenbank.MVC.Entity;

/**
 * Created by Angelo on 03.12.2016.
 */

import java.util.ArrayList;
import java.util.Collection;

public class Timeline extends ArrayList<Post> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3760009921889581657L;

	public Timeline () {
		super();
	}

	public Timeline (Collection<Post> posts) {
		this();
		this.addAll(posts);
	}

}
