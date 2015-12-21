package info.rmapproject.webapp.controllers;

import info.rmapproject.webapp.domain.SearchCommand;

import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/search")
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String searchForm(Model model) {
		logger.info("Search page");
		SearchCommand search = new SearchCommand();
		model.addAttribute("search", search);
		return "search";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String searchResults(@ModelAttribute("search") SearchCommand search,
									BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
		
		if (result.hasErrors()){
			return "search";
		}
		redirectAttributes.addFlashAttribute("search", search);

		String resourceUri = search.getSearch();
		resourceUri = URLDecoder.decode(resourceUri, "UTF-8");
		return "redirect:resources?uri=" + resourceUri; 		
	}
	
}
