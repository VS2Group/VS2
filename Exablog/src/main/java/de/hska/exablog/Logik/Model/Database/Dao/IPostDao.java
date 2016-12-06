package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Model.Entity.Post;
import de.hska.exablog.Logik.Model.Entity.User;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface IPostDao {
	Post createPost(User user, String content);
	void deletePost(long postID);
}
