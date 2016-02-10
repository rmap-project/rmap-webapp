package info.rmapproject.webapp.controllers;

import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.auth.model.User;
import info.rmapproject.webapp.auth.LoginRequired;
import info.rmapproject.webapp.domain.KeyStatus;
import info.rmapproject.webapp.service.UserMgtService;

import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Handles requests related to creation and management of API keys
 * @author khanson
 *
 */
@Controller
@SessionAttributes({"user","account"})
public class ApiKeyController {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	/**Service for user management*/
	@Autowired
	private UserMgtService userMgtService;
	
	/**
	 * GET the list of API keys for the current user
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/keys", method=RequestMethod.GET)
	public String showKeyList(Model model, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user"); //retrieve logged in user
		/*if (user == null || user.getUserId()==0){//no user
			return "redirect:/home";
		}*/
        model.addAttribute("apiKeyList", this.userMgtService.listApiKeyByUser(user.getUserId()));
        return "user/keys";	
	}
	
	/**
	 * GET the form to create a new key
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/key/new", method=RequestMethod.GET)
	public String newKey(Model model, HttpSession session) throws Exception {
		/*User user = (User) session.getAttribute("user"); //retrieve logged in user
		if (user == null || user.getUserId()==0){//no user
			return "redirect:/home";
		}*/
		ApiKey apiKey = new ApiKey();
		model.addAttribute("apiKey", apiKey);
		model.addAttribute("keyStatuses", KeyStatus.values());
		model.addAttribute("targetPage", "keynew");
	    return "user/key";        
	}
	
	/**
	 * Receives the POSTed New API Key form to be processed. Returns any form errors.
	 * @param apiKey
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/key/new", method=RequestMethod.POST)
	public String createKey(@Valid ApiKey apiKey, BindingResult result, ModelMap model, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user"); //retrieve logged in user
		/*if (user == null || user.getUserId()==0){//no user
			return "redirect:/home";
		}*/
        if (result.hasErrors()) {
            return "user/key";
        }
		apiKey.setUserId(user.getUserId());
		this.userMgtService.addApiKey(apiKey);
		return "redirect:/user/keys"; 		
	}
	
	/**
	 * Receives the POSTed New API Key form to be processed. Returns any form errors.
	 * @param apiKey
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/key/edit", method=RequestMethod.GET)
	public String showKeyForm(@RequestParam("keyid") Integer keyId, Model model, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user"); //retrieve logged in user
		/*if (user == null || user.getUserId()==0){//no user
			return "redirect:/home";
		}*/
		ApiKey apiKey = this.userMgtService.getApiKeyById(keyId);
		if (apiKey.getUserId()==user.getUserId())	{
			model.addAttribute("apiKey", this.userMgtService.getApiKeyById(keyId));
			model.addAttribute("targetPage", "keyedit");
	        return "user/key";	
		}
		return "redirect:/user/keys"; 		  
	}
	
	/**
	 * Receives the POSTed Edit API Key form to be processed. Returns any form errors.
	 * @param apiKey
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/key/edit", method=RequestMethod.POST)
	public String updateUser(@Valid ApiKey apiKey, BindingResult result, ModelMap model) throws Exception {
        if (result.hasErrors()) {
            return "user/key";
        }
		this.userMgtService.updateApiKey(apiKey);		
		return "redirect:/user/keys"; 
	}
	
	/**
	 * GET API Key file containing AccessKey and Secret that can be used for API requests
	 * @param keyId
	 * @param response
	 * @throws Exception
	 */
	@LoginRequired
	@RequestMapping(value="/user/key/download", method=RequestMethod.GET)
	public @ResponseBody void downloadKey(@RequestParam("keyid") Integer keyId, 
				HttpServletResponse response, HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user"); //retrieve logged in user
		ApiKey apiKey = this.userMgtService.getApiKeyById(keyId);
		if (apiKey.getUserId()==user.getUserId())	{
			String downloadFileName= "rmap.key";
			String key = apiKey.getAccessKey() + ":" + apiKey.getSecret();
			OutputStream out = response.getOutputStream();
			response.setContentType("text/plain; charset=utf-8");
			response.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
			out.write(key.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		}

	}

		
}
