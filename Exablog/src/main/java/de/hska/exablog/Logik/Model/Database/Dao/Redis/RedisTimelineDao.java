package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Model.Database.Dao.ITimelineDao;
import de.hska.exablog.Logik.Model.Database.RedisDatabase;
import de.hska.exablog.Logik.Model.Entity.Post;
import de.hska.exablog.Logik.Model.Entity.Timeline;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.UserService;
import de.hska.exablog.Logik.Util.StringComparatorByContainedLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Angelo on 04.12.2016.
 */
@Repository
@Qualifier("RedisDatabase")
public class RedisTimelineDao implements ITimelineDao {

	@Autowired
	private RedisDatabase database;

	@Autowired
	private UserService userService;

	@Override
	public Timeline getGlobalTimeline() {

		Set<String> postIds = database.getLatestPostsOps().members(RedisConfig.KEY_FOR_LATEST_POSTS);
		Timeline globalTimeline = new Timeline();

		for (String postId : postIds) {
			if (globalTimeline.size() >= RedisConfig.TIMELINE_LIMIT) {
				break;
			}

			String postKey = "post:" + postId;
			globalTimeline.add(Post.getBuilder()
					.setPostID(Long.parseLong(postId))
					.setContent(database.getPostDataOps().get(postKey, "content"))
					.setUsername(database.getPostDataOps().get(postKey, "username"))
					.setTimestamp(Long.parseLong(database.getPostDataOps().get(postKey, "timestamp")))
					.build());
		}
		return globalTimeline;
	}

	@Override
	public Timeline getPersonalTimeline(User user) {
		Set<String> postIds = this.getUserPostIds(user);
		for (User followed : this.getFollowed(user)) {
			postIds.addAll(this.getUserPostIds(followed));
		}

		List<String> postIdList = new ArrayList<>();
		postIdList.addAll(postIds);
		postIdList.sort(new StringComparatorByContainedLong());
		Timeline personalTimeline = new Timeline();

		for (String postId : postIdList) {
			if (personalTimeline.size() >= RedisConfig.TIMELINE_LIMIT) {
				break;
			}

			String postKey = "post:" + postId;
			personalTimeline.add(Post.getBuilder()
					.setPostID(Long.parseLong(postId))
					.setUsername(database.getPostDataOps().get(postKey, "username"))
					.setContent(database.getPostDataOps().get(postKey, "content"))
					.setTimestamp(Long.parseLong(database.getPostDataOps().get(postKey, "timestamp")))
					.build());
		}

		return personalTimeline;
	}

	private Set<String> getUserPostIds(User user) {
		String key = user.getUsername() + ":posts";
		return database.getUserPostsOps().members(key);
	}

	public Collection<User> getFollowed(User user) {
		String key = user.getUsername() + ":following";
		return this.extractUsers(database.getUserFollowedOps().members(key));
	}

	private Set<User> extractUsers(Set<String> userNames) {
		Set<User> users = new HashSet<>();
		for (String userName : userNames) {
			users.add(userService.getUserByName(userName));
		}
		return users;
	}
}
