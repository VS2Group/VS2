package de.hska.exablog.GUI.Controller;

import de.hska.exablog.Logik.Model.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Angelo on 07.12.2016.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {

	@Autowired
	private SessionService sessionService;

	@RequestMapping(method = RequestMethod.GET)
	public String getLogout(HttpSession session, Model model) {
		sessionService.removeSession(session.getId());
		return "redirect:/login";
	}

}
