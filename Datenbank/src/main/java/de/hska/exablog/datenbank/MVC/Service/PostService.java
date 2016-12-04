package de.hska.exablog.datenbank.MVC.Service;

import de.hska.exablog.datenbank.MVC.Database.Dao.IPostDao;
import de.hska.exablog.datenbank.MVC.Entity.Post;
import de.hska.exablog.datenbank.MVC.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class PostService {

	@Autowired
	IPostDao postDao;

	public Post createPost(User user, String content) {
		return postDao.createPost(user, content);
	}

	public void deletePost(long postID) {
		postDao.deletePost(postID);
	}

}
