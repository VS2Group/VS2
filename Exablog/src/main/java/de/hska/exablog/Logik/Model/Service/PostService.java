package de.hska.exablog.Logik.Model.Service;

import de.hska.exablog.Logik.Model.Database.Dao.IPostDao;
import de.hska.exablog.Logik.Model.Entity.Post;
import de.hska.exablog.Logik.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class PostService {

	@Autowired
	@Qualifier("RedisDatabase")
	IPostDao postDao;

	public Post createPost(User user, String content) {
		return postDao.createPost(user, content);
	}

	public void deletePost(long postID) {
		postDao.deletePost(postID);
	}

}
