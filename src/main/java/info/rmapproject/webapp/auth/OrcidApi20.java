package info.rmapproject.webapp.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.AccessTokenExtractor;
import com.github.scribejava.core.extractors.JsonTokenExtractor;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.utils.OAuthEncoder;

public class OrcidApi20 extends DefaultApi20  {

    private static final String AUTH_URL = "https://www.orcid.org/oauth/authorize/";
    private static final String TOKEN_URL = "https://pub.orcid.org/oauth/token";

    @Override
    public String getAccessTokenEndpoint() {
        return TOKEN_URL + "?grant_type=" + OAuthConstants.AUTHORIZATION_CODE;
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig oAuthConfig) {
        // #show_login skips showing the registration form, which is only
        // cluttersome.
        return String.format(AUTH_URL + "?client_id=%s&scope=%s&response_type=%s&redirect_uri=%s",
            oAuthConfig.getApiKey(), OAuthEncoder.encode(oAuthConfig.getScope()), "code", OAuthEncoder.encode(oAuthConfig.getCallback()));
    }    

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }


}
