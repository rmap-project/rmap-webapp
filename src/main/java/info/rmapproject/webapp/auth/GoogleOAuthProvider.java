package info.rmapproject.webapp.auth;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

public class GoogleOAuthProvider extends OAuthProvider{

	public GoogleOAuthProvider(){}
	
	public GoogleOAuthProvider(OAuthProviderConfig config){
		super(config);
	}

	public OAuthProviderAccount loadOAuthProviderAccount(Token accessToken, OAuthProviderName provider) {
	
		OAuthService service = this.getService();

		// getting user profile
		OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, config.getProfileUrl(), service);
	
		service.signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();
		String jsonString = oauthResponse.getBody();
		JSONObject root = new JSONObject(jsonString);
		JSONArray emailArray = root.getJSONArray("emails");
		JSONObject firstEmail = emailArray.getJSONObject(0);

		String accountId = root.getString("id"); 
		String displayName = root.getString("displayName");
		String publicId = firstEmail.getString("value"); 
		String profilePath="";
		if (root.has("url")){
			profilePath = root.getString("url"); 
		}
		
		OAuthProviderAccount profile = 
				new OAuthProviderAccount(accessToken, provider, displayName, accountId, publicId , profilePath);
	
		//logger.info("Google profile=" + jsonString);
		//logger.info("Google token=" + accessToken.getRawResponse());
		
		return profile;
	}	

	
	
}