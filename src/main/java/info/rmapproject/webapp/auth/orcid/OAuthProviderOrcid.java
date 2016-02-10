package info.rmapproject.webapp.auth.orcid;

import info.rmapproject.webapp.auth.OAuthProvider;
import info.rmapproject.webapp.auth.OAuthProviderAccount;
import info.rmapproject.webapp.auth.OAuthProviderConfig;
import info.rmapproject.webapp.auth.OAuthProviderName;

import org.json.JSONObject;

import com.github.scribejava.core.model.Token;

public class OAuthProviderOrcid extends OAuthProvider {
	
	public OAuthProviderOrcid(){}
	
	public OAuthProviderOrcid(OAuthProviderConfig config){
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