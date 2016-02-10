package info.rmapproject.webapp.service;

import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.auth.model.User;
import info.rmapproject.webapp.auth.OAuthProviderAccount;

import java.util.List;
/**
 * 
 * @author khanson
 *
 */
public interface UserMgtService {
	public void addApiKey(ApiKey apiKey);
	public void updateApiKey(ApiKey apiKey);
	public ApiKey getApiKeyById(int apiKeyId);
	public List<ApiKey> listApiKeyByUser(int userId);
	public int addUser(User user);
	public void updateUserSettings(User user);
	public User getUserById(int userId);
	public User loadUserFromOAuthAccount(OAuthProviderAccount account);
	public int addUserIdentityProvider(int userId, OAuthProviderAccount account);
}
