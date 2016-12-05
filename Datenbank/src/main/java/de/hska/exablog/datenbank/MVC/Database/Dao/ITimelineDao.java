package de.hska.exablog.datenbank.MVC.Database.Dao;

import de.hska.exablog.datenbank.MVC.Entity.Timeline;
import de.hska.exablog.datenbank.MVC.Entity.User;

/**
 * Created by Angelo on 04.12.2016.
 */
public interface ITimelineDao {

	Timeline getGlobalTimeline();
	Timeline getPersonalTimeline(User user);

}
