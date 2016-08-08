package info.rmapproject.webapp.auth;

import org.json.JSONObject;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

public class TwitterOAuthProvider extends OAuthProvider{
	
	private static final String TWITTER_ACCTID_PROPERTY = "id";
	private static final String TWITTER_DISPLAYNAME_PROPERTY = "name";
	private static final String TWITTER_SCREENNAME_PROPERTY = "screen_name";
	
	public TwitterOAuthProvider(){}
	
	public TwitterOAuthProvider(OAuthProviderConfig config){
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

		String accountId = String.valueOf(root.getInt(TWITTER_ACCTID_PROPERTY)); 
		String displayName = root.getString(TWITTER_DISPLAYNAME_PROPERTY);
		String publicId = root.getString(TWITTER_SCREENNAME_PROPERTY); 
		String profilePath = provider.getIdProviderUrl() + "/" + publicId; 
		
		OAuthProviderAccount profile = 
				new OAuthProviderAccount(accessToken, provider, displayName, accountId, publicId , profilePath);

		//logger.info("Twitter profile" + jsonString);
		//logger.info("Twitter token" + accessToken.getRawResponse());
		return profile;
	}	
	
	
}