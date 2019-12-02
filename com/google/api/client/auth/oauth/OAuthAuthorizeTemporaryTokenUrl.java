package com.google.api.client.auth.oauth;

import com.google.api.client.http.*;
import com.google.api.client.util.*;

@Beta
public class OAuthAuthorizeTemporaryTokenUrl extends GenericUrl
{
    @Key("oauth_token")
    public String temporaryToken;
    
    public OAuthAuthorizeTemporaryTokenUrl(final String a1) {
        super(a1);
    }
}
