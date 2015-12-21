package info.rmapproject.webapp.service;

import info.rmapproject.auth.model.AgentType;
import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.auth.model.User;
import info.rmapproject.auth.service.AuthService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author khanson
 *
 */

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

//private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	AuthService authService;
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	
	
	@Override
	public List<AgentType> getAgentTypes() {
		return authService.getAgentTypes();
	}
	
	@Override
	public void addApiKey(ApiKey apiKey) {
		authService.addApiKey(apiKey);
	}
	
	@Override
	public void updateApiKey(ApiKey apiKey) {
		authService.updateApiKey(apiKey);
	}
	
	@Override
	public ApiKey getApiKeyById(int apiKeyId) {
		return authService.getApiKeyById(apiKeyId);
	}
	
	@Override
	public List<ApiKey> listApiKeyByUser(int userId) {
		return authService.listApiKeyByUser(userId);
	}
	
	@Override
	public void addUser(User user) {
		authService.addUser(user);
	}
	
	@Override
	public void updateUserSettings(User user) {
		authService.updateUserSettings(user);
	}
	
	@Override
	public User getUserById(int userId) {
		return authService.getUserById(userId);
	}
	
}
