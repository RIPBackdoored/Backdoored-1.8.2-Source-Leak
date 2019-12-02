package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

@Beta
public static class Builder
{
    Clock clock;
    final HttpTransport transport;
    final JsonFactory jsonFactory;
    String publicCertsEncodedUrl;
    
    public Builder(final HttpTransport a1, final JsonFactory a2) {
        super();
        this.clock = Clock.SYSTEM;
        this.publicCertsEncodedUrl = "https://www.googleapis.com/oauth2/v1/certs";
        this.transport = Preconditions.checkNotNull(a1);
        this.jsonFactory = Preconditions.checkNotNull(a2);
    }
    
    public GooglePublicKeysManager build() {
        return new GooglePublicKeysManager(this);
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getPublicCertsEncodedUrl() {
        return this.publicCertsEncodedUrl;
    }
    
    public Builder setPublicCertsEncodedUrl(final String a1) {
        this.publicCertsEncodedUrl = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public Builder setClock(final Clock a1) {
        this.clock = Preconditions.checkNotNull(a1);
        return this;
    }
}
