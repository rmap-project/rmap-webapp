package info.rmapproject.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ReportController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@RequestMapping(value="/user/reports", method=RequestMethod.GET)
	public String showKeyList(Model model) throws Exception {
        return "user/reports";	
	}
}
