package de.hska.exablog.GUI.Controller.Stomp;

import de.hska.exablog.GUI.Controller.Stomp.Incoming.NewPostRequest;
import de.hska.exablog.GUI.Controller.Stomp.Incoming.RegisterForNewPostsRequest;
import de.hska.exablog.GUI.Controller.Stomp.Outgoing.NewPostReply;
import de.hska.exablog.GUI.Controller.Stomp.Outgoing.RegisterForNewPostsReply;
import de.hska.exablog.GUI.Controller.Stomp.Outgoing.Reply;
import de.hska.exablog.Logik.Model.Database.Messaging.Receiver;
import de.hska.exablog.Logik.Model.Entity.Post;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.PostService;
import de.hska.exablog.Logik.Model.Service.SessionService;
import de.hska.exablog.Logik.Model.Service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;

@Controller
public class StompController {
	@Autowired
	private TimelineService timelineService;

	@Autowired
	private PostService postService;

	@Autowired
	private SimpMessagingTemplate msgTemplate;

	@Autowired
	private SessionService sessionService;

	@MessageMapping("/stomp/register-for-new-posts")
	public void stompRegisterForNewPosts(@NotNull RegisterForNewPostsRequest request) {
		User user = sessionService.validateSession(request.getSessionId());

		if (user != null) {
			Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
			LOGGER.info("INCOMING: /stomp/register-for-new-posts");

			//WebSocketConfig.getStompEndpointRegistry().addEndpoint("/stomp/new-post-request").withSockJS()
			//WebSocketConfig.getRed.addMessageListener(listenerAdapter, new PatternTopic("neue_posts_verfuegbar"));
			timelineService.addNewPostsSubscriber(request.getSessionId());

			msgTemplate.convertAndSend("/topic/posts/registerfornewposts-reply:" + request.getSessionId(),
					new RegisterForNewPostsReply()
			);
		} else {
			msgTemplate.convertAndSend("/topic/posts/registerfornewposts-reply:" + request.getSessionId(),
					new RegisterForNewPostsReply(
							RegisterForNewPostsReply.RequestState.FAILED,
							"Session timed out."
					)
			);
		}
	}


	@MessageMapping("/stomp/new-post-request")
	public void stompCreateNewPost(@NotNull NewPostRequest newPostRequest) {
		User user = sessionService.validateSession(newPostRequest.getSessionId());

		if (user != null) {
			Post post = postService.createPost(user, newPostRequest.getContent());

			msgTemplate.convertAndSend("/topic/posts/new-post-reply:" + newPostRequest.getSessionId(),
					new NewPostReply(post)
			);
		} else {
			msgTemplate.convertAndSend("/topic/posts/new-post-reply:" + newPostRequest.getSessionId(),
					new NewPostReply(
							Reply.RequestState.FAILED,
							"Session timed out."
					)
			);
		}

	}
}
