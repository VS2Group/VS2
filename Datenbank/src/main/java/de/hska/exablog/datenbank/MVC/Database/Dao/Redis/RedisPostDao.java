package de.hska.exablog.datenbank.MVC.Database.Dao.Redis;

import de.hska.exablog.datenbank.MVC.Database.Dao.IPostDao;
import de.hska.exablog.datenbank.MVC.Database.RedisDatabase;
import de.hska.exablog.datenbank.MVC.Entity.Post;
import de.hska.exablog.datenbank.MVC.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by Angelo on 04.12.2016.
 */
@Repository
@Qualifier("RedisDatabase")
public class RedisPostDao implements IPostDao {

	@Autowired
	private RedisDatabase database;

	@Override
	public Post createPost(User user, String content) {
		long postID = database.getPostIDOps().incrementAndGet();
		long timestamp = System.currentTimeMillis();

		String postKey = "post:" + postID;
		database.getPostDataOps().put(postKey, "id", Long.toString(postID));
		database.getPostDataOps().put(postKey, "content", content);
		database.getPostDataOps().put(postKey, "username", user.getUsername());
		database.getPostDataOps().put(postKey, "timestamp", Long.toString(timestamp));

		return Post.getBuilder()
				.setPostID(postID)
				.setUsername(user.getUsername())
				.setContent(content)
				.setTimestamp(timestamp)
				.build();
	}

	@Override
	public void deletePost(long postID) {
		String postKey = "post:" + postID;
		database.getPostDataOps().delete(postKey, "id", "content", "username", "timestamp");
	}
}
