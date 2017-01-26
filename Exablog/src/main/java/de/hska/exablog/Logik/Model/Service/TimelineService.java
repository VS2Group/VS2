package de.hska.exablog.Logik.Model.Service;

import de.hska.exablog.GUI.Controller.Data.TimelineUpdate;
import de.hska.exablog.Logik.Config.RedisConfig;
import de.hska.exablog.Logik.Model.Database.Dao.ITimelineDao;
import de.hska.exablog.Logik.Model.Entity.Session;
import de.hska.exablog.Logik.Model.Entity.Timeline;
import de.hska.exablog.Logik.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class TimelineService {

	@Autowired
	@Qualifier("RedisDatabase")
	private ITimelineDao timelineDao;

	public Timeline getGlobalTimeline(long start) {
		return this.getGlobalTimeline(start, start + RedisConfig.TIMELINE_LIMIT);
	}

	public Timeline getGlobalTimeline(long start, long end) {
		return timelineDao.getGlobalTimeline(start, end);
	}


	public Timeline getPersonalTimeline(User user, long start) {
		return this.getPersonalTimeline(user, start, start + RedisConfig.TIMELINE_LIMIT);
	}

	public Timeline getPersonalTimeline(User user, long start, long end) {
		return timelineDao.getPersonalTimeline(user, start, end);
	}

	public Timeline getDashboardTimeline(User user, long start) {
		return this.getDashboardTimeline(user, start, start + RedisConfig.TIMELINE_LIMIT);
	}

	public Timeline getDashboardTimeline(User user, long start, long end) {
		return timelineDao.getDashboardTimeline(user, start, end);
	}

	public void addNewPostsSubscriber(String sessionId) {
		timelineDao.addNewPostsSubscriber(sessionId);
	}

	public void removeNewPostsSubscriber(String sessionId) {
		timelineDao.removeNewPostsSubscriber(sessionId);
	}

	/**
	 * @return Collection of SessionIDs as Strings
	 */
	public Collection<String> getNewPostsSubscribers() {
		return timelineDao.getNewPostsSubcribers();
	}


}
