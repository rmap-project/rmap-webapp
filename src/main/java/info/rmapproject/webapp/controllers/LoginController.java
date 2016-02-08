package info.rmapproject.webapp.controllers;

import info.rmapproject.auth.model.User;
import info.rmapproject.webapp.auth.OAuthProviderAccount;
import info.rmapproject.webapp.auth.OAuthProviderName;
import info.rmapproject.webapp.auth.google.OAuthProviderGoogle;
import info.rmapproject.webapp.auth.orcid.OAuthProviderOrcid;
import info.rmapproject.webapp.auth.twitter.OAuthProviderTwitter;
import info.rmapproject.webapp.service.UserMgtService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.github.scribejava.core.model.Token;

/**
 * Handles requests related to user management and sign in
 * @author khanson
 *
 */
@Controller
@SessionAttributes({"user","accesstoken"})
public class LoginController {

	/**Service for user management*/
	@Autowired
	private UserMgtService userMgtService;
		
	@Autowired
	@Qualifier("oAuthProviderGoogle")
	private OAuthProviderGoogle oAuthProviderGoogle;

	@Autowired
	@Qualifier("oAuthProviderTwitter")
	private OAuthProviderTwitter oAuthProviderTwitter;

	@Autowired
	@Qualifier("oAuthProviderOrcid")
	private OAuthProviderOrcid oAuthProviderOrcid;
	
		
	@RequestMapping(value={"/user/login/google"}, method = RequestMethod.GET)
	public String logingoogle(HttpSession session) {
		//see if we are already logged in
		Token accessToken = (Token) session.getAttribute("accesstoken");
		if(accessToken == null) {
			//not logged in create service and redirect to google login
			return "redirect:" + oAuthProviderGoogle.getAuthorizationUrl(null);
		}
		//already logged in goto welcome page
		return "redirect:/user/welcome";
	}
	
	@RequestMapping(value={"/user/googlecallback"}, method = RequestMethod.GET)
	public String googlecallback(@RequestParam(value="code", required=false) String oauthVerifier, HttpSession session, Model model) {
				
		OAuthProviderName idProvider = OAuthProviderName.GOOGLE;
		Token accessToken = oAuthProviderGoogle.createAccessToken(null, oauthVerifier);
		// store access token as a session attribute
		session.setAttribute("accesstoken", accessToken);
		
		//load profile from request to service
		OAuthProviderAccount account = 
				oAuthProviderGoogle.loadOAuthProviderAccount(accessToken, idProvider);

		String name = account.getDisplayName();	
		String email = account.getAccountId();
		String idProviderUrl = account.getProviderName().getIdProviderUrl();
		String idProviderId = account.getAccountId();	
		User user = userMgtService.getUserByProviderAccount(idProviderUrl, idProviderId);
				
		if (user==null){
			session.setAttribute("user", new User(name, email, idProviderUrl, idProviderId));
			return "redirect:/user/signup";
		}
		else {
			session.setAttribute("user", user);		
			return "redirect:/user/welcome";
		}
	}
	

	@RequestMapping(value={"/user/login/orcid"}, method = RequestMethod.GET)
	public String loginorcid(HttpSession session) {
		//see if we are already logged in
		Token accessToken = (Token) session.getAttribute("accesstoken");
		if(accessToken == null) {
			//not logged in create service and redirect to orcid login
			return "redirect:" + oAuthProviderOrcid.getAuthorizationUrl(null);
		}
		//already logged in goto welcome page
		return "redirect:/user/welcome";
	}
	
	@RequestMapping(value={"/user/orcidcallback"}, method = RequestMethod.GET)
	public String orcidcallback(@RequestParam(value="code", required=false) String oauthVerifier, HttpSession session, Model model) {
		
		OAuthProviderName idProvider = OAuthProviderName.ORCID;
		Token accessToken = oAuthProviderOrcid.createAccessToken(null, oauthVerifier);
		// store access token as a session attribute
		session.setAttribute("accesstoken", accessToken);
		
		//load profile from request to service
		OAuthProviderAccount account = 
				oAuthProviderOrcid.loadOAuthProviderAccount(accessToken, idProvider);

		String name = account.getDisplayName();	
		String idProviderUrl = account.getProviderName().getIdProviderUrl();
		String idProviderId = account.getAccountId();	
		User user = userMgtService.getUserByProviderAccount(idProviderUrl, idProviderId);
				
		if (user==null){
			session.setAttribute("user", new User(name, idProviderUrl, idProviderId));
			return "redirect:/user/signup";
		}
		else {
			session.setAttribute("user", user);		
			return "redirect:/user/welcome";
		}
	}
	

	@RequestMapping(value={"/user/login/twitter"}, method = RequestMethod.GET)
	public String logintwitter(HttpSession session) {
		//see if we are already logged in
		Token accessToken = (Token) session.getAttribute("accesstoken");
		if(accessToken == null) {
			Token requestToken = oAuthProviderTwitter.createRequestToken();
			session.setAttribute("requesttoken", requestToken);
			//not logged in create service and redirect to twitter login
			return "redirect:" + oAuthProviderTwitter.getAuthorizationUrl(requestToken);
		}
		//already logged in goto welcome page
		return "redirect:/user/welcome";
	}
	
	@RequestMapping(value={"/user/twittercallback"}, method = RequestMethod.GET)
	public String twittercallback(@RequestParam(value="oauth_token", required=false) String oauthToken,
				@RequestParam(value="oauth_verifier", required=false) String oauthVerifier, HttpSession session, Model model) {

		Token requestToken = (Token) session.getAttribute("requesttoken");
		if (requestToken == null){
			return "redirect:/user/login";
		}
		
		OAuthProviderName idProvider = OAuthProviderName.TWITTER;
		Token accessToken = 
				oAuthProviderTwitter.createAccessToken(requestToken,oauthVerifier);
		// store access token as a session attribute
		session.setAttribute("accesstoken", accessToken);
		//load profile from request to service
		OAuthProviderAccount account = 
				oAuthProviderTwitter.loadOAuthProviderAccount(accessToken, idProvider);
				
		String name = account.getDisplayName();	
		String idProviderUrl = account.getProviderName().getIdProviderUrl();
		String idProviderId = account.getAccountId();	
		
		User user = userMgtService.getUserByProviderAccount(idProviderUrl, idProviderId);
		if (user==null){
			session.setAttribute("user", new User(name, idProviderUrl, idProviderId));
			return "redirect:/user/signup";
		}
		else {
			session.setAttribute("user", user);		
			return "redirect:/user/welcome";
		}
	}
	
		
	/**
	 * Return page that shows login options
	 * @return Login options page
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.GET)
	public String loginPage(Model model, HttpSession session) {
		return "user/login";
	}
		
}
