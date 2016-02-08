package info.rmapproject.webapp.auth.orcid;

import info.rmapproject.webapp.auth.OAuthProviderAccount;
import info.rmapproject.webapp.auth.OAuthProviderConfig;
import info.rmapproject.webapp.auth.OAuthProviderName;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

public class OAuthProviderOrcid {

	protected static final Logger logger = LoggerFactory.getLogger(OAuthProviderOrcid.class);

	protected OAuthProviderConfig config;
	
	protected OAuthService service = null;
	
	public OAuthProviderOrcid(){}
	
	public OAuthProviderOrcid(OAuthProviderConfig config){
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
		//example of rawResponse:
		//{"access_token":"####-#####-#####-####","token_type":"bearer","refresh_token":"####-#####-#####-####",
		//"expires_in":631138518,"scope":"/authenticate","name":"Karen L. Hanson","orcid":"0000-0002-9354-8328"}

		String jsonString = accessToken.getRawResponse();
		JSONObject root = new JSONObject(jsonString);		
		String displayName = root.getString("name");
		String orcid = root.getString("orcid"); 
		
		OAuthProviderAccount profile = new OAuthProviderAccount();

		profile.setAccountId(orcid);
		profile.setDisplayName(displayName);
		profile.setProviderName(provider);
	
		return profile;

	}	

	
	
}