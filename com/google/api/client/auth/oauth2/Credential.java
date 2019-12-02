package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import java.util.concurrent.locks.*;
import java.io.*;
import java.util.logging.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class Credential implements HttpExecuteInterceptor, HttpRequestInitializer, HttpUnsuccessfulResponseHandler
{
    static final Logger LOGGER;
    private final Lock lock;
    private final AccessMethod method;
    private final Clock clock;
    private String accessToken;
    private Long expirationTimeMilliseconds;
    private String refreshToken;
    private final HttpTransport transport;
    private final HttpExecuteInterceptor clientAuthentication;
    private final JsonFactory jsonFactory;
    private final String tokenServerEncodedUrl;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private final HttpRequestInitializer requestInitializer;
    
    public Credential(final AccessMethod a1) {
        this(new Builder(a1));
    }
    
    protected Credential(final Builder a1) {
        super();
        this.lock = new ReentrantLock();
        this.method = Preconditions.checkNotNull(a1.method);
        this.transport = a1.transport;
        this.jsonFactory = a1.jsonFactory;
        this.tokenServerEncodedUrl = ((a1.tokenServerUrl == null) ? null : a1.tokenServerUrl.build());
        this.clientAuthentication = a1.clientAuthentication;
        this.requestInitializer = a1.requestInitializer;
        this.refreshListeners = Collections.unmodifiableCollection((Collection<? extends CredentialRefreshListener>)a1.refreshListeners);
        this.clock = Preconditions.checkNotNull(a1.clock);
    }
    
    @Override
    public void intercept(final HttpRequest v2) throws IOException {
        this.lock.lock();
        try {
            final Long a1 = this.getExpiresInSeconds();
            if (this.accessToken == null || (a1 != null && a1 <= 60L)) {
                this.refreshToken();
                if (this.accessToken == null) {
                    return;
                }
            }
            this.method.intercept(v2, this.accessToken);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public boolean handleResponse(final HttpRequest v1, final HttpResponse v2, final boolean v3) {
        boolean v4 = false;
        boolean v5 = false;
        final List<String> v6 = v2.getHeaders().getAuthenticateAsList();
        if (v6 != null) {
            for (final String a1 : v6) {
                if (a1.startsWith("Bearer ")) {
                    v5 = true;
                    v4 = BearerToken.INVALID_TOKEN_ERROR.matcher(a1).find();
                    break;
                }
            }
        }
        if (!v5) {
            v4 = (v2.getStatusCode() == 401);
        }
        if (v4) {
            try {
                this.lock.lock();
                try {
                    return !Objects.equal(this.accessToken, this.method.getAccessTokenFromRequest(v1)) || this.refreshToken();
                }
                finally {
                    this.lock.unlock();
                }
            }
            catch (IOException a2) {
                Credential.LOGGER.log(Level.SEVERE, "unable to refresh token", a2);
            }
        }
        return false;
    }
    
    @Override
    public void initialize(final HttpRequest a1) throws IOException {
        a1.setInterceptor(this);
        a1.setUnsuccessfulResponseHandler(this);
    }
    
    public final String getAccessToken() {
        this.lock.lock();
        try {
            return this.accessToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setAccessToken(final String a1) {
        this.lock.lock();
        try {
            this.accessToken = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public final AccessMethod getMethod() {
        return this.method;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }
    
    public final String getRefreshToken() {
        this.lock.lock();
        try {
            return this.refreshToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setRefreshToken(final String a1) {
        this.lock.lock();
        try {
            if (a1 != null) {
                Preconditions.checkArgument(this.jsonFactory != null && this.transport != null && this.clientAuthentication != null && this.tokenServerEncodedUrl != null, (Object)"Please use the Builder and call setJsonFactory, setTransport, setClientAuthentication and setTokenServerUrl/setTokenServerEncodedUrl");
            }
            this.refreshToken = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public final Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setExpirationTimeMilliseconds(final Long a1) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = a1;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public final Long getExpiresInSeconds() {
        this.lock.lock();
        try {
            if (this.expirationTimeMilliseconds == null) {
                return null;
            }
            return (this.expirationTimeMilliseconds - this.clock.currentTimeMillis()) / 1000L;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setExpiresInSeconds(final Long a1) {
        return this.setExpirationTimeMilliseconds((a1 == null) ? null : Long.valueOf(this.clock.currentTimeMillis() + a1 * 1000L));
    }
    
    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }
    
    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }
    
    public final boolean refreshToken() throws IOException {
        this.lock.lock();
        try {
            try {
                final TokenResponse executeRefreshToken = this.executeRefreshToken();
                if (executeRefreshToken != null) {
                    this.setFromTokenResponse(executeRefreshToken);
                    for (final CredentialRefreshListener v1 : this.refreshListeners) {
                        v1.onTokenResponse(this, executeRefreshToken);
                    }
                    return true;
                }
            }
            catch (TokenResponseException ex) {
                final boolean v2 = 400 <= ex.getStatusCode() && ex.getStatusCode() < 500;
                if (ex.getDetails() != null && v2) {
                    this.setAccessToken(null);
                    this.setExpiresInSeconds(null);
                }
                for (final CredentialRefreshListener v3 : this.refreshListeners) {
                    v3.onTokenErrorResponse(this, ex.getDetails());
                }
                if (v2) {
                    throw ex;
                }
            }
            return false;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setFromTokenResponse(final TokenResponse a1) {
        this.setAccessToken(a1.getAccessToken());
        if (a1.getRefreshToken() != null) {
            this.setRefreshToken(a1.getRefreshToken());
        }
        this.setExpiresInSeconds(a1.getExpiresInSeconds());
        return this;
    }
    
    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.refreshToken == null) {
            return null;
        }
        return new RefreshTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), this.refreshToken).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).execute();
    }
    
    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }
    
    static {
        LOGGER = Logger.getLogger(Credential.class.getName());
    }
    
    public static class Builder
    {
        final AccessMethod method;
        HttpTransport transport;
        JsonFactory jsonFactory;
        GenericUrl tokenServerUrl;
        Clock clock;
        HttpExecuteInterceptor clientAuthentication;
        HttpRequestInitializer requestInitializer;
        Collection<CredentialRefreshListener> refreshListeners;
        
        public Builder(final AccessMethod a1) {
            super();
            this.clock = Clock.SYSTEM;
            this.refreshListeners = (Collection<CredentialRefreshListener>)Lists.newArrayList();
            this.method = Preconditions.checkNotNull(a1);
        }
        
        public Credential build() {
            return new Credential(this);
        }
        
        public final AccessMethod getMethod() {
            return this.method;
        }
        
        public final HttpTransport getTransport() {
            return this.transport;
        }
        
        public Builder setTransport(final HttpTransport a1) {
            this.transport = a1;
            return this;
        }
        
        public final Clock getClock() {
            return this.clock;
        }
        
        public Builder setClock(final Clock a1) {
            this.clock = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public Builder setJsonFactory(final JsonFactory a1) {
            this.jsonFactory = a1;
            return this;
        }
        
        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }
        
        public Builder setTokenServerUrl(final GenericUrl a1) {
            this.tokenServerUrl = a1;
            return this;
        }
        
        public Builder setTokenServerEncodedUrl(final String a1) {
            this.tokenServerUrl = ((a1 == null) ? null : new GenericUrl(a1));
            return this;
        }
        
        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }
        
        public Builder setClientAuthentication(final HttpExecuteInterceptor a1) {
            this.clientAuthentication = a1;
            return this;
        }
        
        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }
        
        public Builder setRequestInitializer(final HttpRequestInitializer a1) {
            this.requestInitializer = a1;
            return this;
        }
        
        public Builder addRefreshListener(final CredentialRefreshListener a1) {
            this.refreshListeners.add(Preconditions.checkNotNull(a1));
            return this;
        }
        
        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }
        
        public Builder setRefreshListeners(final Collection<CredentialRefreshListener> a1) {
            this.refreshListeners = Preconditions.checkNotNull(a1);
            return this;
        }
    }
    
    public interface AccessMethod
    {
        void intercept(final HttpRequest p0, final String p1) throws IOException;
        
        String getAccessTokenFromRequest(final HttpRequest p0);
    }
}
