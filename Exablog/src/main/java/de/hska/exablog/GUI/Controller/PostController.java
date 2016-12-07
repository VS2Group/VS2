package de.hska.exablog.GUI.Controller;

import de.hska.exablog.GUI.Controller.Data.Greeting;
import de.hska.exablog.GUI.Controller.Data.Message;
import de.hska.exablog.GUI.Controller.Data.PostData;
import de.hska.exablog.Logik.Model.Entity.User;
import de.hska.exablog.Logik.Model.Service.PostService;
import de.hska.exablog.Logik.Model.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Angelo on 07.12.2016.
 */
@Controller
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private PostService postService;

	@RequestMapping(method = RequestMethod.GET)
	public String getPost(HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User war noch eingeloggt
			return "redirect:/login";
		}
		return "redirect:/timeline";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postPost(@ModelAttribute PostData postData, HttpSession session, Model model) {
		User user = sessionService.validateSession(session.getId());
		if (user == null) {    // User war noch eingeloggt
			return "redirect:/login";
		}

		if (model.containsAttribute("postData")) {
			postService.createPost(user, postData.getPost().getContent());


			return "redirect:/timeline";
			//return "redirect:/" + postData.getComingfrom();
		}

		return "redirect:/timeline";
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(Message message) throws Exception {
		return new Greeting("Hello, " + message.getContent() + "!");
	}
}