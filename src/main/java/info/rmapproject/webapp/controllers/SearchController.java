package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.domain.SearchCommand;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles display of the search page 
 * @author khanson
 *
 */
@Controller
@SessionAttributes({"user","account"})
@RequestMapping(value="/search")
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	/**
	 * GETs the search form
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String searchForm(Model model) {
		logger.info("Search page");
		SearchCommand search = new SearchCommand();
		model.addAttribute("search", search);
		return "search";
	}
	
	/**
	 * Processes the POSTed search form
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String searchResults(@ModelAttribute("search") SearchCommand search,
									BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
		
		if (result.hasErrors()){
			return "search";
		}
		redirectAttributes.addFlashAttribute("search", search);

		String resourceUri = search.getSearch();
		//may or may not be encoded, so to make sure decode first then encode again
		resourceUri = URLDecoder.decode(resourceUri, "UTF-8");
		return "redirect:/resources/" + URLEncoder.encode(resourceUri, "UTF-8");
	}
	
}
