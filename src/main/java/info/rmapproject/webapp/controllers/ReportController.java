package info.rmapproject.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Handles display of reports
 * @author khanson
 *
 */
@Controller
@SessionAttributes({"user","account"})
public class ReportController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * GETs the report list
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/reports", method=RequestMethod.GET)
	public String showKeyList(Model model) throws Exception {
        return "user/reports";	
	}
}
