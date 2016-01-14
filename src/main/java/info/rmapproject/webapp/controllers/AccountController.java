package info.rmapproject.webapp.controllers;

import info.rmapproject.auth.exception.RMapAuthException;
import info.rmapproject.auth.model.User;
import info.rmapproject.auth.model.UserAgentType;
import info.rmapproject.auth.model.UserAgentUri;
import info.rmapproject.webapp.service.UserMgtService;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Handles requests related to user management and sign in
 * @author khanson
 *
 */
@Controller
@SessionAttributes("user")
public class AccountController {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**Service for user management*/
	@Autowired
	private UserMgtService userMgtService;
	
	

	/**
	 * NOT CURRENTLY USED - MAY DELETE IF NOT ADDED BACK IN
	 * Converts String result from form fields to their relevant object types and binds the field to the model.
	 * Specifically String to UserAgentUri object and String to UserAgentType object
	 * @param request
	 * @param binder
	 * @throws Exception
	 */
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
	
	
	/***************************
	 * 
	 * 		Web pages
	 * 
	 ***************************/
	
	/**
	 * Get the Sign In page
	 * @return Sign In page
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.GET)
	public String loginPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null && user.getUserId()>0){
			return "redirect:/user/welcome";
		}
		model.addAttribute("user", new User());
		return "/user/login";
	}
	
	/**
	 * Receives the POSTed Sign In form to be processed. Returns any form errors.
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public String loginUser(@ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) throws Exception {
        //TODO: temporary just to test user... need to incorporate OAUTH stuff.
		User fulluserdets = null;
		if (result.hasErrors()) {
            return "user/login";
        }
        try {
        	fulluserdets = this.userMgtService.getUserById(user.getUserId()); //refresh record
        }
        catch (RMapAuthException ex) {
            return "user/login";
        }
        model.addAttribute("user", fulluserdets); //store it in the session
        
		return "redirect:/user/welcome";			
	}	
	

	/**
	 * Get the welcome page after sign in
	 * @return Welcome page
	 */
	@RequestMapping(value="/user/welcome", method=RequestMethod.GET)
	public String welcomePage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null || user.getUserId()==0){ //no user logged in
			return "redirect:/home";
		}
		return "/user/welcome";
	}
	
	/**
	 * Get the Sign Up Form page
	 * @param model
	 * @return the Sign Up Form page
	 */
	@RequestMapping(value="/user/signup", method=RequestMethod.GET)
	public String signupForm(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null && user.getUserId()>0){
			return "redirect:/user/welcome";
		}
		model.addAttribute("user", new User());
		return "/user/signup";
	}
	
	/**
	 * Receives the POSTed Sign Up form to be processed. Returns any form errors.
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/signup", method=RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult result, HttpSession session, Model model) throws Exception {
        if (result.hasErrors()) {
            return "user/signup";
        }
		int userId = this.userMgtService.addUser(user);
		user = this.userMgtService.getUserById(userId); //refresh record
		model.addAttribute("user", user); //save latest user details to sesson
		return "redirect:/user/welcome"; 		
	}	
	
	/**
	 * Get the User Settings form
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/settings", method=RequestMethod.GET)
	public String settingsForm(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null || user.getUserId()==0){
			return "redirect:/home";
		}
        return "/user/settings";	
	}
	
	/**
	 * Receives the POSTed Settings form to be processed. Returns any form errors.
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/settings", method=RequestMethod.POST)
	public String updateUserSettings(@ModelAttribute("user") User user, BindingResult result, ModelMap model) throws Exception {
        if (result.hasErrors()) {
            return "user/settings";
        }
		this.userMgtService.updateUserSettings(user);
		user = this.userMgtService.getUserById(user.getUserId()); //refresh record
		model.addAttribute("user", user); //save latest user details to session
		return "/user/settings"; 		
	}

	/**
	 * Logs out the user by completing the session.
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/logout", method=RequestMethod.GET)
	public String logout(SessionStatus status) throws Exception {
		status.setComplete();
		return "redirect:/home"; 		
	}		
	

	/**
	 * Cancels sign in, purges session data
	 * @return home page
	 */
	@RequestMapping(value="/user/logincancel", method=RequestMethod.GET)
	public String cancelLogin(SessionStatus status) {
		status.setComplete();
		return "redirect:/home";
	}	

	/**
	 * Cancels sign in, purges session data
	 * @return home page
	 */
	@RequestMapping(value="/user/settingscancel", method=RequestMethod.GET)
	public String cancelSettingsEdits(SessionStatus status) {
		return "redirect:/user/welcome";
	}
	
	
}
