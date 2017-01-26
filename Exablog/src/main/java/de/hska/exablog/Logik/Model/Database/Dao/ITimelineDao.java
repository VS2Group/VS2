package de.hska.exablog.Logik.Model.Database.Dao;

import de.hska.exablog.Logik.Model.Entity.Timeline;
import de.hska.exablog.Logik.Model.Entity.User;

import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface ITimelineDao {
	Timeline getGlobalTimeline(long start, long end);
	Timeline getPersonalTimeline(User user, long start, long end);

	Timeline getDashboardTimeline(User user, long start, long end);

	void addNewPostsSubscriber(String sessionId);

	void removeNewPostsSubscriber(String sessionId);

	Collection<String> getNewPostsSubcribers();


}
