package info.rmapproject.webapp.auth.twitter;

import info.rmapproject.webapp.auth.OAuthProviderAccount;
import info.rmapproject.webapp.auth.OAuthProviderConfig;
import info.rmapproject.webapp.auth.OAuthProviderName;
import info.rmapproject.webapp.auth.google.OAuthProviderGoogle;

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

public class OAuthProviderTwitter {

	protected static final Logger logger = LoggerFactory.getLogger(OAuthProviderTwitter.class);

	protected OAuthProviderConfig config;
	
	protected OAuthService service = null;
	
	public OAuthProviderTwitter(){}
	
	public OAuthProviderTwitter(OAuthProviderConfig config){
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
		service.signRequest(accessToken, oauthRequest); // the access token from step 4
	
		Response oauthResponse = oauthRequest.send();

		String jsonString = oauthResponse.getBody();
		JSONObject root = new JSONObject(jsonString);
		
		String displayName = root.getString("name");
		String screename = root.getString("screen_name"); 
		
		OAuthProviderAccount profile = new OAuthProviderAccount();
			
		profile.setAccountId(screename);
		profile.setDisplayName(displayName);
		profile.setProviderName(OAuthProviderName.TWITTER);
		
		return profile;
	}	
	
	
}