package de.hska.exablog.Logik.Model.Database.Dao.Redis;

import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Exception.UserDoesNotExistException;
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

	@Autowired
	private RedisUserDao userDao;

	@Override
	public Timeline getGlobalTimeline(long start, long end) {
		List<String> sortedPostsGlobal = database.getSortedPostsOps().range("sortedPostsGlobal", start, end);

		//Set<String> postIds = database.getLatestPostsOps().members(RedisConfig.KEY_FOR_LATEST_POSTS);
		Timeline globalTimeline = new Timeline();

		for (String postId : sortedPostsGlobal) {
			String postKey = "post:" + postId;
			try {
				globalTimeline.getPosts().add(Post.getBuilder()
						.setPostID(Long.parseLong(postId))
						.setContent(database.getPostDataOps().get(postKey, "content"))
						.setUser(userService.getUserByName(database.getPostDataOps().get(postKey, "username")))
						.setTimestamp(new Date(Long.parseLong(database.getPostDataOps().get(postKey, "timestamp"))))
						.build());
			} catch (UserDoesNotExistException e) {
				e.printStackTrace();
			}
		}
		return globalTimeline;
	}

	@Override
	public Timeline getPersonalTimeline(User user, long start, long end) {
		Set<String> postIds = this.getUserPostIds(user);
		for (User followed : userDao.getFollowings(user)) {
			postIds.addAll(this.getUserPostIds(followed));
		}

		List<String> postIdList = new ArrayList<>();
		postIdList.addAll(postIds);
		postIdList.sort(new StringComparatorByContainedLong());
		Timeline personalTimeline = new Timeline();

		// Count backwards and stop at timeline limit or zero
		for (int i = postIdList.size() - 1, count = 0; i >= 0 && count < RedisConfig.TIMELINE_LIMIT; i--, count++) {
			String postId = postIdList.get(i);

			if (personalTimeline.getPosts().size() >= RedisConfig.TIMELINE_LIMIT) {
				break;
			}

			String postKey = "post:" + postId;
			try {
				personalTimeline.getPosts().add(Post.getBuilder()
						.setPostID(Long.parseLong(postId))
						.setUser(userService.getUserByName(database.getPostDataOps().get(postKey, "username")))
						.setContent(database.getPostDataOps().get(postKey, "content"))
						.setTimestamp(new Date(Long.parseLong(database.getPostDataOps().get(postKey, "timestamp"))))
						.build());
			} catch (UserDoesNotExistException e) {
				e.printStackTrace();
			}
		}

		return personalTimeline;
	}

	@Override
	public Timeline getDashboardTimeline(User user, long start, long end) {
		Set<String> postIds = this.getUserPostIds(user);
		List<String> postIdList = new ArrayList<>();
		postIdList.addAll(postIds);
		postIdList.sort(new StringComparatorByContainedLong());

		Timeline dashboardTimeline = new Timeline();

		// Count backwards and stop at timeline limit or zero
		for (int i = postIdList.size() - 1, count = 0; i >= 0 && count < RedisConfig.TIMELINE_LIMIT; i--, count++) {
			String postId = postIdList.get(i);

			if (dashboardTimeline.getPosts().size() >= RedisConfig.TIMELINE_LIMIT) {
				break;
			}

			String postKey = "post:" + postId;
			try {
				dashboardTimeline.getPosts().add(Post.getBuilder()
						.setPostID(Long.parseLong(postId))
						.setUser(userService.getUserByName(database.getPostDataOps().get(postKey, "username")))
						.setContent(database.getPostDataOps().get(postKey, "content"))
						.setTimestamp(new Date(Long.parseLong(database.getPostDataOps().get(postKey, "timestamp"))))
						.build());
			} catch (UserDoesNotExistException e) {
				e.printStackTrace();
			}
		}

		return dashboardTimeline;
	}

	@Override
	public void addNewPostsSubscriber(String sessionId) {
		database.getNewPostSubscribersOps().add("newpostsubscribers", sessionId);
	}

	@Override
	public void removeNewPostsSubscriber(String sessionId) {
		database.getNewPostSubscribersOps().remove("newpostsubscribers", sessionId);
	}

	@Override
	public Collection<String> getNewPostsSubcribers() {
		return database.getNewPostSubscribersOps().members("newpostsubscribers"); // TODO
	}

	private Set<String> getUserPostIds(User user) {
		String key = user.getUsername() + ":posts";
		return database.getUserPostsOps().members(key);
	}
}
