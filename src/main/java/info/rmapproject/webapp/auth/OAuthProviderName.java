package info.rmapproject.webapp.auth;

public enum OAuthProviderName {
	GOOGLE ("https://www.google.com"),
	ORCID ("http://orcid.org"),
	TWITTER ("https://twitter.com/");
	
	private final String idProviderUrl;

	private OAuthProviderName (String idProviderUrl) {
		this.idProviderUrl = idProviderUrl;
	}
	public String getIdProviderUrl()  {
		return idProviderUrl;
	}
}
