package info.rmapproject.webapp.auth;

import org.json.JSONObject;
import org.springframework.context.annotation.Scope;

import com.github.scribejava.core.model.Token;

@Scope("session")
public class OrcidOAuthProvider extends OAuthProvider {
	
	public OrcidOAuthProvider(){}
	
	public OrcidOAuthProvider(OAuthProviderConfig config){
		super(config);
	}

	public OAuthProviderAccount loadOAuthProviderAccount(Token accessToken, OAuthProviderName provider) {
		//example of rawResponse:
		//{"access_token":"####-#####-#####-####","token_type":"bearer","refresh_token":"####-#####-#####-####",
		//"expires_in":631138518,"scope":"/authenticate","name":"Karen L. Hanson","orcid":"0000-0002-9354-8328"}

		String jsonString = accessToken.getRawResponse();
		JSONObject root = new JSONObject(jsonString);		
		String displayName = root.getString("name");
		String accountId = root.getString("orcid"); 
		String publicId = provider.getIdProviderUrl() + "/" + accountId; 
		String profilePath = publicId;
		
		OAuthProviderAccount profile = 
				new OAuthProviderAccount(accessToken, provider, displayName, accountId, publicId , profilePath);
		
		return profile;

	}	

	
	
}