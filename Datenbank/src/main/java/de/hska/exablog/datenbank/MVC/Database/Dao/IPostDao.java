package de.hska.exablog.datenbank.MVC.Database.Dao;

import de.hska.exablog.datenbank.MVC.Entity.Post;
import de.hska.exablog.datenbank.MVC.Entity.User;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface IPostDao {
	Post createPost(User user, String content);
	void deletePost(long postID);
}
