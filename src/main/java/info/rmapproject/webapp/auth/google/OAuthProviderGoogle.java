package info.rmapproject.webapp.auth.google;

import info.rmapproject.webapp.auth.OAuthProviderAccount;
import info.rmapproject.webapp.auth.OAuthProviderConfig;
import info.rmapproject.webapp.auth.OAuthProviderName;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

public class OAuthProviderGoogle {

	protected static final Logger logger = LoggerFactory.getLogger(OAuthProviderGoogle.class);

	protected OAuthProviderConfig config;
	
	protected OAuthService service = null;
	
	public OAuthProviderGoogle(){}
	
	public OAuthProviderGoogle(OAuthProviderConfig config){
		this.config = config;
	}

	public void setConfig(OAuthProviderConfig config){
		this.config = config;
	}
	
	@SuppressWarnings("unchecked")
	public OAuthService getService() {
		if (this.service == null){
			if (config.getScope().length()>0){
				this.service = new ServiceBuilder().provider(config.getApiClass())
						.apiKey(config.getApiKey())
					    .apiSecret(config.getApiSecret())
					    .callback(config.getCallback())
					    .scope(config.getScope())
					    .build();				
			}
			else {
				this.service = new ServiceBuilder().provider(config.getApiClass())
						.apiKey(config.getApiKey())
					    .apiSecret(config.getApiSecret())
					    .callback(config.getCallback())
					    .build();							
			}
		}
		return service;
	}

	public String getAuthorizationUrl(Token requestToken) {		
		return this.getService().getAuthorizationUrl(requestToken);
	}

	public Token createRequestToken() {	
		return this.getService().getRequestToken();
	}

	//for oauth2 requestToken is null	
	public Token createAccessToken(Token requestToken,String oauthVerifier) {
		// create access token
		Verifier verifier = new Verifier(oauthVerifier);
		Token accessToken = this.getService().getAccessToken(requestToken, verifier);
		return accessToken;
	}
	
	public OAuthProviderAccount loadOAuthProviderAccount(Token accessToken, OAuthProviderName provider) {
	
		OAuthService service = this.getService();

		// getting user profile
		OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, config.getProfileUrl(), service);
	
		logger.debug(provider.getIdProviderUrl());
		logger.debug(config.getProfileUrl());
		
		service.signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();
		String jsonString = oauthResponse.getBody();
		JSONObject root = new JSONObject(jsonString);
		JSONArray emailArray = root.getJSONArray("emails");
		JSONObject firstEmail = emailArray.getJSONObject(0);
		
		String displayName = root.getString("displayName");
		String email = firstEmail.getString("value"); 
		
		OAuthProviderAccount profile = new OAuthProviderAccount();
		
		profile.setAccountId(email);
		profile.setDisplayName(displayName);
		profile.setProviderName(provider);
	
		return profile;
	}	

	
	
}