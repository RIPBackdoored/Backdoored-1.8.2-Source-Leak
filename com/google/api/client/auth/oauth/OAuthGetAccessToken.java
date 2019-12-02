package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;

@Beta
public class OAuthGetAccessToken extends AbstractOAuthGetToken
{
    public String temporaryToken;
    public String verifier;
    
    public OAuthGetAccessToken(final String a1) {
        super(a1);
    }
    
    @Override
    public OAuthParameters createParameters() {
        final OAuthParameters v1 = super.createParameters();
        v1.token = this.temporaryToken;
        v1.verifier = this.verifier;
        return v1;
    }
}
