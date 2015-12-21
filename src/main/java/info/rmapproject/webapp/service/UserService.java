package info.rmapproject.webapp.service;

import info.rmapproject.auth.model.AgentType;
import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.auth.model.User;

import java.util.List;
/**
 * 
 * @author khanson
 *
 */
public interface UserService {
	public List<AgentType> getAgentTypes();
	public void addApiKey(ApiKey apiKey);
	public void updateApiKey(ApiKey apiKey);
	public ApiKey getApiKeyById(int apiKeyId);
	public List<ApiKey> listApiKeyByUser(int userId);
	public void addUser(User user);
	public void updateUserSettings(User user);
	public User getUserById(int userId);
}
