package info.rmapproject.webapp.service;

import info.rmapproject.auth.model.AgentType;
import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.auth.model.User;
import info.rmapproject.auth.service.RMapAuthService;
import info.rmapproject.auth.service.RMapAuthServiceFactory;

import java.util.List;

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
	
	private static RMapAuthService rMapAuthService=null;
	
	private void init() {
		if (rMapAuthService ==null) {
			rMapAuthService = RMapAuthServiceFactory.createService();			
		}
	}
		
	@Override
	public List<AgentType> getAgentTypes() {
		init();
		return rMapAuthService.getAgentTypes();
	}
	
	@Override
	public void addApiKey(ApiKey apiKey) {
		init();
		rMapAuthService.addApiKey(apiKey);
	}
	
	@Override
	public void updateApiKey(ApiKey apiKey) {
		init();
		rMapAuthService.updateApiKey(apiKey);
	}
	
	@Override
	public ApiKey getApiKeyById(int apiKeyId) {
		init();
		return rMapAuthService.getApiKeyById(apiKeyId);
	}
	
	@Override
	public List<ApiKey> listApiKeyByUser(int userId) {
		init();
		return rMapAuthService.listApiKeyByUser(userId);
	}
	
	@Override
	public void addUser(User user) {
		init();
		rMapAuthService.addUser(user);
	}
	
	@Override
	public void updateUserSettings(User user) {
		init();
		rMapAuthService.updateUserSettings(user);
	}
	
	@Override
	public User getUserById(int userId) {
		init();
		return rMapAuthService.getUserById(userId);
	}
	
}
