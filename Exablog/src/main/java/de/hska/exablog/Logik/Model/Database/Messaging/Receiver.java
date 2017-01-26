package de.hska.exablog.Logik.Model.Database.Messaging;

import de.hska.exablog.GUI.Controller.Stomp.Outgoing.NewPostsAvailableNotification;
import de.hska.exablog.Logik.Model.Entity.Post;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.TimelineService;
import de.hska.exablog.Logik.Model.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	private static int lastReceivedPost;
	@Autowired
	private TimelineService timelineService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private SimpMessagingTemplate msgTemplate;
	private CountDownLatch latch;
	@Autowired
	private UserService userService;

	@Autowired
	public Receiver(CountDownLatch latch) {
		this.latch = latch;
	}

	public void receiveMessage(String message) {
		LOGGER.info(String.format("Received <%s>", message));
		//long lastUpdate = Long.parseLong(message.split(";")[0]);
		int anzahl = Integer.parseInt(message.split(";")[1]);

		List<Post> postsSinceLastUpdate = timelineService.getGlobalTimeline(0, anzahl - 1).getPosts();

		// Iterate through all subscribers
		Iterator<String> iterator = timelineService.getNewPostsSubscribers().iterator();
		while (iterator.hasNext()) {
			String sessionId = iterator.next();

			// If subscribers session is valid: notify subscriber, else: remove session from subscribers
			User user = sessionService.validateOnlySession(sessionId);
			if (user != null) {

				Collection<User> followings = userService.getFollowings(user);
				for (Post post : postsSinceLastUpdate) {
					if (followings.contains(post.getUser())) {
						msgTemplate.convertAndSend("/topic/posts/newposts:" + sessionId,
								new NewPostsAvailableNotification()
						);
						break;
					}
				}


			} else {
				timelineService.removeNewPostsSubscriber(sessionId);
			}
		}

		latch.countDown();
	}
}
