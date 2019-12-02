package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import java.security.*;
import java.io.*;
import com.google.api.client.auth.openidconnect.*;
import java.util.*;
import com.google.api.client.util.*;

@Beta
public class GoogleIdTokenVerifier extends IdTokenVerifier
{
    private final GooglePublicKeysManager publicKeys;
    
    public GoogleIdTokenVerifier(final HttpTransport a1, final JsonFactory a2) {
        this(new Builder(a1, a2));
    }
    
    public GoogleIdTokenVerifier(final GooglePublicKeysManager a1) {
        this(new Builder(a1));
    }
    
    protected GoogleIdTokenVerifier(final Builder a1) {
        super(a1);
        this.publicKeys = a1.publicKeys;
    }
    
    public final GooglePublicKeysManager getPublicKeysManager() {
        return this.publicKeys;
    }
    
    public final HttpTransport getTransport() {
        return this.publicKeys.getTransport();
    }
    
    public final JsonFactory getJsonFactory() {
        return this.publicKeys.getJsonFactory();
    }
    
    @Deprecated
    public final String getPublicCertsEncodedUrl() {
        return this.publicKeys.getPublicCertsEncodedUrl();
    }
    
    @Deprecated
    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        return this.publicKeys.getPublicKeys();
    }
    
    @Deprecated
    public final long getExpirationTimeMilliseconds() {
        return this.publicKeys.getExpirationTimeMilliseconds();
    }
    
    public boolean verify(final GoogleIdToken v0) throws GeneralSecurityException, IOException {
        if (!super.verify(v0)) {
            return false;
        }
        for (final PublicKey a1 : this.publicKeys.getPublicKeys()) {
            if (v0.verifySignature(a1)) {
                return true;
            }
        }
        return false;
    }
    
    public GoogleIdToken verify(final String a1) throws GeneralSecurityException, IOException {
        final GoogleIdToken v1 = GoogleIdToken.parse(this.getJsonFactory(), a1);
        return this.verify(v1) ? v1 : null;
    }
    
    @Deprecated
    public GoogleIdTokenVerifier loadPublicCerts() throws GeneralSecurityException, IOException {
        this.publicKeys.refresh();
        return this;
    }
    
    @Beta
    public static class Builder extends IdTokenVerifier.Builder
    {
        GooglePublicKeysManager publicKeys;
        
        public Builder(final HttpTransport a1, final JsonFactory a2) {
            this(new GooglePublicKeysManager(a1, a2));
        }
        
        public Builder(final GooglePublicKeysManager a1) {
            super();
            this.publicKeys = Preconditions.checkNotNull(a1);
            this.setIssuers(Arrays.asList("accounts.google.com", "https://accounts.google.com"));
        }
        
        @Override
        public GoogleIdTokenVerifier build() {
            return new GoogleIdTokenVerifier(this);
        }
        
        public final GooglePublicKeysManager getPublicCerts() {
            return this.publicKeys;
        }
        
        public final HttpTransport getTransport() {
            return this.publicKeys.getTransport();
        }
        
        public final JsonFactory getJsonFactory() {
            return this.publicKeys.getJsonFactory();
        }
        
        @Deprecated
        public final String getPublicCertsEncodedUrl() {
            return this.publicKeys.getPublicCertsEncodedUrl();
        }
        
        @Deprecated
        public Builder setPublicCertsEncodedUrl(final String a1) {
            this.publicKeys = new GooglePublicKeysManager.Builder(this.getTransport(), this.getJsonFactory()).setPublicCertsEncodedUrl(a1).setClock(this.publicKeys.getClock()).build();
            return this;
        }
        
        @Override
        public Builder setIssuer(final String a1) {
            return (Builder)super.setIssuer(a1);
        }
        
        @Override
        public Builder setIssuers(final Collection<String> a1) {
            return (Builder)super.setIssuers(a1);
        }
        
        @Override
        public Builder setAudience(final Collection<String> a1) {
            return (Builder)super.setAudience(a1);
        }
        
        @Override
        public Builder setAcceptableTimeSkewSeconds(final long a1) {
            return (Builder)super.setAcceptableTimeSkewSeconds(a1);
        }
        
        @Override
        public Builder setClock(final Clock a1) {
            return (Builder)super.setClock(a1);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setAcceptableTimeSkewSeconds(final long acceptableTimeSkewSeconds) {
            return this.setAcceptableTimeSkewSeconds(acceptableTimeSkewSeconds);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setAudience(final Collection audience) {
            return this.setAudience(audience);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setIssuers(final Collection issuers) {
            return this.setIssuers(issuers);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setIssuer(final String issuer) {
            return this.setIssuer(issuer);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setClock(final Clock clock) {
            return this.setClock(clock);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier build() {
            return this.build();
        }
    }
}
