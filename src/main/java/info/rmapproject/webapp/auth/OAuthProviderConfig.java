package info.rmapproject.webapp.auth;
	

@SuppressWarnings("rawtypes")
public class OAuthProviderConfig {
	
	private OAuthProviderName providerName;
	private String apiKey;
	private String apiSecret;
	private String callback;
	private Class apiClass;
	private String scope;
	private String profileUrl;
	
	public OAuthProviderConfig() {
	}
	
	public OAuthProviderConfig(OAuthProviderName providerName, String apiKey, String apiSecret, String callback, 
								Class apiClass, String scope, String profileUrl) {
	    super();
	    this.setProviderName(providerName);
	    this.setApiKey(apiKey);
	    this.setApiSecret(apiSecret);
	    this.setCallback(callback);
	    this.setApiClass(apiClass);
	    this.setScope(scope);
	    this.setProfileUrl(profileUrl);
    }
	
	//no scope
	public OAuthProviderConfig(OAuthProviderName providerName, String apiKey, String apiSecret, String callback, 
								Class apiClass, String profileUrl) {
	    super();
	    this.setProviderName(providerName);
	    this.setApiKey(apiKey);
	    this.setApiSecret(apiSecret);
	    this.setCallback(callback);
	    this.setApiClass(apiClass);
	    this.setScope("");
	    this.setProfileUrl(profileUrl);
    }
	

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Class getApiClass() {
		return apiClass;
	}

	public void setApiClass(Class apiClass) {
		this.apiClass = apiClass;
	}
	

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public OAuthProviderName getProviderName() {
		return providerName;
	}

	public void setProviderName(OAuthProviderName providerName) {
		this.providerName = providerName;
	}

}
