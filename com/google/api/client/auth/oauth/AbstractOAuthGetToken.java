package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public abstract class AbstractOAuthGetToken extends GenericUrl
{
    public HttpTransport transport;
    public String consumerKey;
    public OAuthSigner signer;
    protected boolean usePost;
    
    protected AbstractOAuthGetToken(final String a1) {
        super(a1);
    }
    
    public final OAuthCredentialsResponse execute() throws IOException {
        final HttpRequestFactory v1 = this.transport.createRequestFactory();
        final HttpRequest v2 = v1.buildRequest(this.usePost ? "POST" : "GET", this, null);
        this.createParameters().intercept(v2);
        final HttpResponse v3 = v2.execute();
        v3.setContentLoggingLimit(0);
        final OAuthCredentialsResponse v4 = new OAuthCredentialsResponse();
        UrlEncodedParser.parse(v3.parseAsString(), v4);
        return v4;
    }
    
    public OAuthParameters createParameters() {
        final OAuthParameters v1 = new OAuthParameters();
        v1.consumerKey = this.consumerKey;
        v1.signer = this.signer;
        return v1;
    }
}
