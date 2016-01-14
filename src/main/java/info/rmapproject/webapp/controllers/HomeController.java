package info.rmapproject.webapp.controllers;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Handles display of the home and contact pages
 * @author khanson
 *
 */
@Controller
@SessionAttributes("user")
public class HomeController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * GETs the Home page
	 */
	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		//logger.info("Welcome home! The client locale is {}.", locale);
				
		return "home";
	}
	
	/**
	 * GETs the Contact page
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/contact"}, method = RequestMethod.GET)
	public String home(Model model) {
				
		return "contact";
	}
	
}
