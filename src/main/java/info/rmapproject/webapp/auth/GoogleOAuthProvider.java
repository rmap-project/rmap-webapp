package info.rmapproject.webapp.auth;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

@Scope("session")
public class GoogleOAuthProvider extends OAuthProvider{
	
	private static final String GOOGLE_JSON_EMAILLIST_PROPERTY = "emails";
	private static final String GOOGLE_JSON_ACCOUNTID_PROPERTY = "id";
	private static final String GOOGLE_JSON_DISPLAYNAME_PROPERTY = "displayName";
	private static final String GOOGLE_JSON_EMAIL_PROPERTY = "value";
	private static final String GOOGLE_JSON_PROFILEPATH_PROPERTY = "url";
	
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
		JSONArray emailArray = root.getJSONArray(GOOGLE_JSON_EMAILLIST_PROPERTY);
		JSONObject firstEmail = emailArray.getJSONObject(0);

		String accountId = root.getString(GOOGLE_JSON_ACCOUNTID_PROPERTY); 
		String displayName = root.getString(GOOGLE_JSON_DISPLAYNAME_PROPERTY);
		String publicId = firstEmail.getString(GOOGLE_JSON_EMAIL_PROPERTY); 
		String profilePath="";
		if (root.has(GOOGLE_JSON_PROFILEPATH_PROPERTY)){
			profilePath = root.getString(GOOGLE_JSON_PROFILEPATH_PROPERTY); 
		}
		
		OAuthProviderAccount profile = 
				new OAuthProviderAccount(accessToken, provider, displayName, accountId, publicId , profilePath);
	
		//logger.info("Google profile=" + jsonString);
		//logger.info("Google token=" + accessToken.getRawResponse());
		
		return profile;
	}	

	
	
}