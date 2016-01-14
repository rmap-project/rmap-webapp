package info.rmapproject.webapp.service;

import info.rmapproject.auth.model.AgentType;
import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.auth.model.User;
import info.rmapproject.auth.service.RMapAuthService;
import info.rmapproject.auth.service.RMapAuthServiceFactory;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author khanson
 *
 */

@Service("userMgtService")
@Transactional
public class UserMgtServiceImpl implements UserMgtService {

//private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static RMapAuthService rMapAuthService;
	
	@PostConstruct
	private void init() {
		if (rMapAuthService ==null) {
			rMapAuthService = RMapAuthServiceFactory.createService();			
		}
	}
		
	@Override
	public List<AgentType> getAgentTypes() {
		return rMapAuthService.getAgentTypes();
	}
	
	@Override
	public void addApiKey(ApiKey apiKey) {
		rMapAuthService.addApiKey(apiKey);
	}
	
	@Override
	public void updateApiKey(ApiKey apiKey) {
		rMapAuthService.updateApiKey(apiKey);
	}
	
	@Override
	public ApiKey getApiKeyById(int apiKeyId) {
		return rMapAuthService.getApiKeyById(apiKeyId);
	}
	
	@Override
	public List<ApiKey> listApiKeyByUser(int userId) {
		return rMapAuthService.listApiKeyByUser(userId);
	}
	
	@Override
	public int addUser(User user) {
		return rMapAuthService.addUser(user);
	}
	
	@Override
	public void updateUserSettings(User user) {
		rMapAuthService.updateUserSettings(user);
	}
	
	@Override
	public User getUserById(int userId) {
		return rMapAuthService.getUserById(userId);
	}
	
}
