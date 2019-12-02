package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;

@Beta
public final class OAuthCredentialsResponse
{
    @Key("oauth_token")
    public String token;
    @Key("oauth_token_secret")
    public String tokenSecret;
    @Key("oauth_callback_confirmed")
    public Boolean callbackConfirmed;
    
    public OAuthCredentialsResponse() {
        super();
    }
}
