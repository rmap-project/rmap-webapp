package info.rmapproject.webapp.controllers;

import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.webapp.domain.KeyStatus;
import info.rmapproject.webapp.service.UserService;

import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ApiKeyController {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/user/keys", method=RequestMethod.GET)
	public String showKeyList(Model model) throws Exception {
		//TODO:hardcoded id for now... need to replace this with the actual user Id when we have had auth setup
		int userId = 3;
		model.addAttribute("user", this.userService.getUserById(userId));
        model.addAttribute("apiKeyList", this.userService.listApiKeyByUser(userId));
        return "/user/keys";	
	}
	
	@RequestMapping(value="/user/key/new", method=RequestMethod.GET)
	public String newKey(Model model) throws Exception {
		//TODO:hardcoded id for now... need to replace this with the actual user Id when we have had auth setup
		int userId = 3;
		ApiKey apiKey = new ApiKey();
		apiKey.setUserId(userId);
		model.addAttribute("apiKey", apiKey);
		model.addAttribute("keyStatuses", KeyStatus.values());
		model.addAttribute("targetPage", "keynew");
	    return "/user/key";        
	}
		
	@RequestMapping(value="/user/key/new", method=RequestMethod.POST)
	public String createKey(@ModelAttribute("apiKey") ApiKey apiKey) throws Exception {
		this.userService.addApiKey(apiKey);
		return "redirect:/user/keys"; 		
	}
	
	@RequestMapping(value="/user/key/edit", method=RequestMethod.GET)
	public String showKeyForm(@RequestParam("keyid") Integer keyId, Model model) throws Exception {
		//TODO:hardcoded id for now... need to replace this with the actual user Id when we have had auth setup
		int userid = 3;
		ApiKey apiKey = this.userService.getApiKeyById(keyId);
		if (apiKey.getUserId()==userid)	{
			model.addAttribute("apiKey", this.userService.getApiKeyById(keyId));
			model.addAttribute("targetPage", "keyedit");
	        return "user/key";	
		}
		return "redirect:/user/keys"; 		  
	}
	
	@RequestMapping(value="/user/key/edit", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("apiKey") ApiKey apiKey) throws Exception {
		this.userService.updateApiKey(apiKey);		
		return "redirect:/user/keys"; 
	}
	
	@RequestMapping(value="/user/key/download", method=RequestMethod.GET)
	public @ResponseBody void downloadKey(@RequestParam("keyid") Integer keyId, HttpServletResponse response) throws Exception {
		//TODO:hardcoded id for now... need to replace this with the actual user Id when we have had auth setup
		int userid = 3;
		ApiKey apiKey = this.userService.getApiKeyById(keyId);
		if (apiKey.getUserId()==userid)	{
			String downloadFileName= "rmap.key";
			String key = apiKey.getAccessKey() + ":" + apiKey.getSecret();
			OutputStream out = response.getOutputStream();
			response.setContentType("text/plain; charset=utf-8");
			response.addHeader("Content-Disposition","attachment; filename=\"" + downloadFileName + "\"");
			out.write(key.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		}

		/*else {
			return "redirect:/user/keys"; 	
		}*/
	}

		
}
