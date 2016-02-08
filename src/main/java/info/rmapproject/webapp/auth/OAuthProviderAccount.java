package info.rmapproject.webapp.auth;

import com.github.scribejava.core.model.Token;


public class OAuthProviderAccount {

	private OAuthProviderName providerName;
	private Token accessToken;
	private String displayName;
	private String accountId;
	
	public OAuthProviderAccount() {
	}
	
	public OAuthProviderAccount(Token accessToken, OAuthProviderName providerName, String displayName, String accountId) {
	    this.setAccessToken(accessToken);
	    this.setProviderName(providerName);
	    this.setDisplayName(displayName);
	    this.setAccountId(accountId);
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
	
}
