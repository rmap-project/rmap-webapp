package info.rmapproject.webapp.auth;

import com.github.scribejava.core.model.Token;

/** 
 * Class to store relevant OAuth information for duration of web session 
 * @author khanson
 *
 */
public class OAuthProviderAccount {

	/** oauth provider name from OAuthProviderName enum **/
	private OAuthProviderName providerName;
	
	/** oauth access token**/
	private Token accessToken;
	
	/** account id that uniquely identifiers user in ID provider system **/
	private String accountId;
	
	/** Publicly visible ID that identifiers user e.g. twitter handle, ORCID
	 * This can be the same as the accountId **/
	private String accountPublicId;
	
	/** Publicly visible display name for user according to ID provider system **/
	private String displayName;
	
	/** URL pointing to profile (where available) **/
	private String profilePath;
	
	public OAuthProviderAccount() {
	}
	
	public OAuthProviderAccount(Token accessToken, OAuthProviderName providerName, 
								String displayName, String accountId, 
								String accountPublicId, String profilePath) {
	    this.setAccessToken(accessToken);
	    this.setProviderName(providerName);
	    this.setDisplayName(displayName);
	    this.setAccountId(accountId);
	    this.setAccountPublicId(accountPublicId);
	    this.setProfilePath(profilePath);
    }
	
	public OAuthProviderName getProviderName() {
		return providerName;
	}
	public void setProviderName(OAuthProviderName provider) {
		this.providerName = provider;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountName) {
		this.accountId = accountName;
	}
	public Token getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(Token accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccountPublicId() {
		return accountPublicId;
	}

	public void setAccountPublicId(String accountPublicId) {
		this.accountPublicId = accountPublicId;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}
	
	public String getProviderUrl(){
		return this.providerName.getIdProviderUrl();
	}
	
}
