package com.google.api.client.auth.oauth;

import com.google.api.client.http.*;
import com.google.api.client.util.*;

@Beta
public class OAuthCallbackUrl extends GenericUrl
{
    @Key("oauth_token")
    public String token;
    @Key("oauth_verifier")
    public String verifier;
    
    public OAuthCallbackUrl(final String a1) {
        super(a1);
    }
}
