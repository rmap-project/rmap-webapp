package info.rmapproject.webapp.auth.twitter;

import info.rmapproject.webapp.auth.OAuthProvider;
import info.rmapproject.webapp.auth.OAuthProviderAccount;
import info.rmapproject.webapp.auth.OAuthProviderConfig;
import info.rmapproject.webapp.auth.OAuthProviderName;

import org.json.JSONObject;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

public class OAuthProviderTwitter extends OAuthProvider{
	
	public OAuthProviderTwitter(){}
	
	public OAuthProviderTwitter(OAuthProviderConfig config){
		super(config);
	}

	public OAuthProviderAccount loadOAuthProviderAccount(Token accessToken, OAuthProviderName provider) {
		OAuthService service = this.getService();
	
		// getting user profile
		OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, config.getProfileUrl(), service);
		service.signRequest(accessToken, oauthRequest); // the access token from step 4
	
		Response oauthResponse = oauthRequest.send();

		String jsonString = oauthResponse.getBody();
		JSONObject root = new JSONObject(jsonString);

		String accountId = root.getString("id"); 
		String displayName = root.getString("name");
		String publicId = root.getString("screen_name"); 
		String profilePath = provider.getIdProviderUrl() + "/" + publicId; 
		
		OAuthProviderAccount profile = 
				new OAuthProviderAccount(accessToken, provider, displayName, accountId, publicId , profilePath);

		//logger.info("Twitter profile" + jsonString);
		//logger.info("Twitter token" + accessToken.getRawResponse());
		return profile;
	}	
	
	
}