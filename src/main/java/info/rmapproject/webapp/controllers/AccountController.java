package info.rmapproject.webapp.controllers;

import info.rmapproject.auth.model.User;
import info.rmapproject.webapp.auth.LoginRequired;
import info.rmapproject.webapp.auth.OAuthProviderAccount;
import info.rmapproject.webapp.service.UserMgtService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
@SessionAttributes({"user","account"})
public class AccountController {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**Service for user management*/
	@Autowired
	private UserMgtService userMgtService;
	
	

//	/**
//	 * NOT CURRENTLY USED - MAY DELETE IF NOT ADDED BACK IN
//	 * Converts String result from form fields to their relevant object types and binds the field to the model.
//	 * Specifically String to UserAgentUri object and String to UserAgentType object
//	 * @param request
//	 * @param binder
//	 * @throws Exception
//	 */
//	@InitBinder("user") 
//	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
//		
//		binder.registerCustomEditor(Set.class, "userAgentUris", new CustomCollectionEditor(Set.class) {
//			@Override
//			protected Object convertElement(Object element) {
//				String uri = (String) element;
//				UserAgentUri userAgentUri = new UserAgentUri();
//				userAgentUri.setUri(uri);
//				return userAgentUri;
//			}
//			});
//		
//		binder.registerCustomEditor(Set.class, "userAgentTypes", new CustomCollectionEditor(Set.class) {
//			@Override
//			protected Object convertElement(Object element) {
//			String uri = (String) element;
//			UserAgentType userAgentType = new UserAgentType();
//			userAgentType.setUri(uri);
//			return userAgentType;
//			}
//			});
//	}	
	
	
	/***************************
	 * 
	 * 		Web pages
	 * 
	 ***************************/

	/**
	 * Get the welcome page after sign in
	 * @return Welcome page
	 */
	@LoginRequired
	@RequestMapping(value="/user/welcome", method=RequestMethod.GET)
	public String welcomePage(Model model, HttpSession session) {
		return "/user/welcome";
	}
	
	/**
	 * Get the Sign Up Form page
	 * @param model
	 * @return the Sign Up Form page
	 */
	@LoginRequired
	@RequestMapping(value="/user/signup", method=RequestMethod.GET)
	public String signupForm(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null && user.getUserId()>0){
			return "redirect:/user/welcome";
		}
		model.addAttribute("user", user);
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
	@LoginRequired
	@RequestMapping(value="/user/signup", method=RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult result, HttpSession session, Model model) throws Exception {
        if (result.hasErrors()) {
            return "user/signup";
        }
		int userId = this.userMgtService.addUser(user);
		user = this.userMgtService.getUserById(userId); //refresh record
		
		OAuthProviderAccount account = (OAuthProviderAccount) session.getAttribute("account");	
		//create identity provider that is associated with the account
		this.userMgtService.addUserIdentityProvider(userId, account);
		
		session.setAttribute("user", user); //save latest user details to session
		return "redirect:/user/welcome"; 		
	}	
	
	/**
	 * Get the User Settings form
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/settings", method=RequestMethod.GET)
	public String settingsForm(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null || user.getUserId()==0){
			return "redirect:/home";
		}
		user = this.userMgtService.getUserById(user.getUserId()); //refresh record to make sure editing latest
		model.addAttribute("user",user);
        return "user/settings";	
	}
	
	/**
	 * Receives the POSTed Settings form to be processed. Returns any form errors.
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/settings", method=RequestMethod.POST)
	public String updateUserSettings(@ModelAttribute("user") User user, BindingResult result, ModelMap model) throws Exception {
        if (result.hasErrors()) {
            return "user/settings";
        }
		this.userMgtService.updateUserSettings(user);
		user = this.userMgtService.getUserById(user.getUserId()); //refresh record
		model.addAttribute("user", user); //save latest user details to session
		return "user/settings"; 		
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
	 * Page to create disco (placeholder)
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/user/disco", method=RequestMethod.GET)
	public String createdisco(SessionStatus status) throws Exception {
		return "user/disco"; 		
	}		
	
	
}
