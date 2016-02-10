package info.rmapproject.webapp.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

public abstract class OAuthProvider {

	protected static final Logger logger = LoggerFactory.getLogger(OAuthProvider.class);

	protected OAuthProviderConfig config;
	protected OAuthService service = null;

	public OAuthProvider() {
		super();
	}
	
	public OAuthProvider(OAuthProviderConfig config) {
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

	public abstract OAuthProviderAccount loadOAuthProviderAccount(Token accessToken, OAuthProviderName provider);

}