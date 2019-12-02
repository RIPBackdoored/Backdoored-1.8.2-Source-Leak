package com.google.api.client.auth.oauth2;

import java.util.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;

public class TokenRequest extends GenericData
{
    HttpRequestInitializer requestInitializer;
    HttpExecuteInterceptor clientAuthentication;
    private final HttpTransport transport;
    private final JsonFactory jsonFactory;
    private GenericUrl tokenServerUrl;
    @Key("scope")
    private String scopes;
    @Key("grant_type")
    private String grantType;
    
    public TokenRequest(final HttpTransport a1, final JsonFactory a2, final GenericUrl a3, final String a4) {
        super();
        this.transport = Preconditions.checkNotNull(a1);
        this.jsonFactory = Preconditions.checkNotNull(a2);
        this.setTokenServerUrl(a3);
        this.setGrantType(a4);
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }
    
    public TokenRequest setRequestInitializer(final HttpRequestInitializer a1) {
        this.requestInitializer = a1;
        return this;
    }
    
    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }
    
    public TokenRequest setClientAuthentication(final HttpExecuteInterceptor a1) {
        this.clientAuthentication = a1;
        return this;
    }
    
    public final GenericUrl getTokenServerUrl() {
        return this.tokenServerUrl;
    }
    
    public TokenRequest setTokenServerUrl(final GenericUrl a1) {
        this.tokenServerUrl = a1;
        Preconditions.checkArgument(a1.getFragment() == null);
        return this;
    }
    
    public final String getScopes() {
        return this.scopes;
    }
    
    public TokenRequest setScopes(final Collection<String> a1) {
        this.scopes = ((a1 == null) ? null : Joiner.on(' ').join(a1));
        return this;
    }
    
    public final String getGrantType() {
        return this.grantType;
    }
    
    public TokenRequest setGrantType(final String a1) {
        this.grantType = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final HttpResponse executeUnparsed() throws IOException {
        final HttpRequestFactory v1 = this.transport.createRequestFactory(new HttpRequestInitializer() {
            final /* synthetic */ TokenRequest this$0;
            
            TokenRequest$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public void initialize(final HttpRequest a1) throws IOException {
                if (this.this$0.requestInitializer != null) {
                    this.this$0.requestInitializer.initialize(a1);
                }
                final HttpExecuteInterceptor v1 = a1.getInterceptor();
                a1.setInterceptor(new HttpExecuteInterceptor() {
                    final /* synthetic */ HttpExecuteInterceptor val$interceptor;
                    final /* synthetic */ TokenRequest$1 this$1;
                    
                    TokenRequest$1$1() {
                        this.this$1 = a1;
                        super();
                    }
                    
                    @Override
                    public void intercept(final HttpRequest a1) throws IOException {
                        if (v1 != null) {
                            v1.intercept(a1);
                        }
                        if (this.this$1.this$0.clientAuthentication != null) {
                            this.this$1.this$0.clientAuthentication.intercept(a1);
                        }
                    }
                });
            }
        });
        final HttpRequest v2 = v1.buildPostRequest(this.tokenServerUrl, new UrlEncodedContent(this));
        v2.setParser(new JsonObjectParser(this.jsonFactory));
        v2.setThrowExceptionOnExecuteError(false);
        final HttpResponse v3 = v2.execute();
        if (v3.isSuccessStatusCode()) {
            return v3;
        }
        throw TokenResponseException.from(this.jsonFactory, v3);
    }
    
    public TokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(TokenResponse.class);
    }
    
    @Override
    public TokenRequest set(final String a1, final Object a2) {
        return (TokenRequest)super.set(a1, a2);
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
}
