package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;

@Beta
public class OAuthGetTemporaryToken extends AbstractOAuthGetToken
{
    public String callback;
    
    public OAuthGetTemporaryToken(final String a1) {
        super(a1);
    }
    
    @Override
    public OAuthParameters createParameters() {
        final OAuthParameters v1 = super.createParameters();
        v1.callback = this.callback;
        return v1;
    }
}
