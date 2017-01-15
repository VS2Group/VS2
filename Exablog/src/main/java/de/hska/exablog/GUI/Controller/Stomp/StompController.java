package de.hska.exablog.GUI.Controller.Stomp;

import de.hska.exablog.GUI.Controller.Stomp.Incoming.NewPostRequest;
import de.hska.exablog.GUI.Controller.Stomp.Outgoing.NewPostReply;
import de.hska.exablog.GUI.Controller.Stomp.Outgoing.Reply;
import de.hska.exablog.Logik.Model.Entity.Post;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.PostService;
import de.hska.exablog.Logik.Model.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;

@Controller
public class StompController {
	@Autowired
	private PostService postService;

	@Autowired
	private SimpMessagingTemplate msgTemplate;

	@Autowired
	private SessionService sessionService;


	@MessageMapping("/stomp/new-post-request")
	public void stompCreateNewPost(@NotNull NewPostRequest newPostRequest) {
		User user = sessionService.validateSession(newPostRequest.getSessionId());

		if (user != null) {
			Post post = postService.createPost(user, newPostRequest.getContent());

			msgTemplate.convertAndSend("/topic/posts/new-post-reply",
					new NewPostReply(post)
			);
		} else {
			msgTemplate.convertAndSend("/topic/posts/new-post-reply",
					new NewPostReply(
							Reply.RequestState.FAILED,
							"Not yet implemented."
					)
			);
		}

	}
}
