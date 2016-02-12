package info.rmapproject.webapp.service;

import info.rmapproject.auth.model.ApiKey;
import info.rmapproject.auth.model.User;
import info.rmapproject.auth.model.UserIdentityProvider;
import info.rmapproject.auth.service.RMapAuthService;
import info.rmapproject.auth.service.RMapAuthServiceFactory;
import info.rmapproject.webapp.auth.OAuthProviderAccount;

import java.util.Date;
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
		if (user.hasRMapAgent() && user.isDoRMapAgentSync()){
			// update the RMap Agent
			rMapAuthService.createOrUpdateAgentFromUser(user);
		}
	}
	
	@Override
	public User getUserById(int userId) {
		return rMapAuthService.getUserById(userId);
	}
	
	@Override
	public User loadUserFromOAuthAccount(OAuthProviderAccount account){
		String idProviderUrl = account.getProviderName().getIdProviderUrl();
		String idProviderId = account.getAccountId();	

		//first attempt to load id provider
		UserIdentityProvider userIdProvider = rMapAuthService.getUserIdProvider(idProviderUrl, idProviderId);
		
		if (userIdProvider == null) {
			return null;
		}		

		//update any details that may have changed since last login, and update last auth date
		userIdProvider.setLastAuthenticatedDate(new Date());
		userIdProvider.setProviderAccountPublicId(account.getAccountPublicId());
		userIdProvider.setProviderAccountDisplayName(account.getDisplayName());
		userIdProvider.setProviderAccountProfileUrl(account.getProfilePath());
		userIdProvider.setLastAuthenticatedDate(new Date());
		rMapAuthService.updateUserIdProvider(userIdProvider);
		
		//TODO: need to throw exception if no user found.
		
		//get the user associated with the idprovider login and update the accessed date for the user
		User user = rMapAuthService.getUserById(userIdProvider.getUserId());
		user.setLastAccessedDate(new Date());
		rMapAuthService.updateUser(user);
		return user;
		
	}
	
	@Override
	public int addUserIdentityProvider(int userId, OAuthProviderAccount account) {
		UserIdentityProvider newAccount = new UserIdentityProvider();
		
		newAccount.setUserId(userId);
		newAccount.setIdentityProviderId(account.getProviderName().getIdProviderUrl());
		newAccount.setProviderAccountPublicId(account.getAccountPublicId());
		newAccount.setProviderAccountId(account.getAccountId());
		newAccount.setProviderAccountDisplayName(account.getDisplayName());
		newAccount.setProviderAccountProfileUrl(account.getProfilePath());
		newAccount.setCreatedDate(new Date());
		newAccount.setLastAuthenticatedDate(new Date());
				
		return rMapAuthService.addUserIdProvider(newAccount);		
	}
	
	
}
