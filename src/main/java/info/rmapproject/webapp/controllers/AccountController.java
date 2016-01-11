package info.rmapproject.webapp.controllers;

import info.rmapproject.auth.model.User;
import info.rmapproject.auth.model.UserAgentType;
import info.rmapproject.auth.model.UserAgentUri;
import info.rmapproject.webapp.service.UserMgtService;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AccountController {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserMgtService userMgtService;
	
	@RequestMapping(value="/user/signin", method=RequestMethod.GET)
	public String signInPage() {
		return "/user/signin";
	}

	@RequestMapping(value="/user/welcome", method=RequestMethod.GET)
	public String welcomePage() {
		return "/user/welcome";
	}
	
	
	@RequestMapping(value="/user/signup", method=RequestMethod.GET)
	public String signupForm(Model model) {
		model.addAttribute(new User());
       // model.addAttribute("KEY_STATUSES", new KeyStatus());
       //model.addAttribute("agentTypes", this.userMgtService.getAgentTypes());
		return "/user/signup";
	}
	
	@InitBinder("user") 
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		
	binder.registerCustomEditor(Set.class, "userAgentUris", new CustomCollectionEditor(Set.class) {
		@Override
		protected Object convertElement(Object element) {
			String uri = (String) element;
			UserAgentUri userAgentUri = new UserAgentUri();
			userAgentUri.setUri(uri);
			return userAgentUri;
		}
		});
	
	binder.registerCustomEditor(Set.class, "userAgentTypes", new CustomCollectionEditor(Set.class) {
		@Override
		protected Object convertElement(Object element) {
		String uri = (String) element;
		UserAgentType userAgentType = new UserAgentType();
		userAgentType.setUri(uri);
		return userAgentType;
		}
		});
	}	
	
	@RequestMapping(value="/user/signup", method=RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult result, ModelMap model) throws Exception {
        if (result.hasErrors()) {
            //model.addAttribute("agentTypes", this.userMgtService.getAgentTypes());
            return "user/signup";
        }
		this.userMgtService.addUser(user);
		return "redirect:/user/welcome"; 		
	}	
	
	@RequestMapping(value="/user/settings", method=RequestMethod.GET)
	public String settingsForm(Model model) throws Exception {
		//TODO:hardcoded id for now... need to replace this with the actual user Id when we have had auth setup
		int id = 3;
        model.addAttribute("user", this.userMgtService.getUserById(id));
        model.addAttribute("agentTypes", this.userMgtService.getAgentTypes());
        return "/user/settings";	
	}
	
	@RequestMapping(value="/user/settings", method=RequestMethod.POST)
	public String updateUserSettings(@ModelAttribute("user") User user, BindingResult result, ModelMap model) throws Exception {
        if (result.hasErrors()) {
            //model.addAttribute("agentTypes", this.userMgtService.getAgentTypes());
            return "user/signup";
        }
		this.userMgtService.updateUserSettings(user);
		return "/user/settings"; 		
	}
		
}
