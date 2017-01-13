package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Model.Database.Dao.IPostDao;
import de.hska.exablog.Logik.Model.Database.RedisDatabase;
import de.hska.exablog.Logik.Model.Entity.Post;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Angelo on 04.12.2016.
 */
@Repository
@Qualifier("RedisDatabase")
public class RedisPostDao implements IPostDao {

	@Autowired
	private RedisDatabase database;
	@Autowired
	private UserService userService;


	@Override
	public Post createPost(User user, String content) {
		long postID = database.getPostIDOps().incrementAndGet();
		long timestamp = System.currentTimeMillis();

		// Create entry in post-table
		String postKey = "post:" + postID;
		String userPostsKey = user.getUsername() + ":posts";

		database.getPostDataOps().put(postKey, "id", Long.toString(postID));
		database.getUserPostsOps().add(userPostsKey, Long.toString(postID)); //Add to user:posts
		database.getPostDataOps().put(postKey, "content", content);
		database.getPostDataOps().put(postKey, "username", user.getUsername());
		database.getPostDataOps().put(postKey, "timestamp", Long.toString(timestamp));

		// Create entry in global timeline table
		database.getSortedPostsOps().leftPush("sortedPostsGlobal", Long.toString(postID));

		// Create entry in each followers timeline table
		for (User follower : userService.getFollowers(user)) {
			database.getSortedPostsOps().leftPush("sortedPostsPersonal:" + follower.getUsername(), Long.toString(postID));
		}

		return Post.getBuilder()
				.setPostID(postID)
				.setUser(user)
				.setContent(content)
				.setTimestamp(new Date(timestamp))
				.build();
	}

}
