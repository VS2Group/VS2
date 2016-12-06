package de.hska.exablog.Logik.Model.Service;

import de.hska.exablog.Logik.Model.Database.Dao.ITimelineDao;
import de.hska.exablog.Logik.Model.Entity.Timeline;
import de.hska.exablog.Logik.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Angelo on 04.12.2016.
 */
@Service
public class TimelineService {

	@Autowired
	@Qualifier("RedisDatabase")
	private ITimelineDao timelineDao;

	public Timeline getGlobalTimeline() {
		return timelineDao.getGlobalTimeline();
	}

	public Timeline getPersonalTimeline(User user) {
		return timelineDao.getPersonalTimeline(user);
	}
}
