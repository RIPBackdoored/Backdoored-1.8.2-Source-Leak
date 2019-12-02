package com.google.api.client.googleapis.auth.oauth2;

import java.util.concurrent.locks.*;
import java.security.*;
import com.google.api.client.util.*;
import java.io.*;
import java.util.*;
import java.security.cert.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.regex.*;

@Beta
public class GooglePublicKeysManager
{
    private static final long REFRESH_SKEW_MILLIS = 300000L;
    private static final Pattern MAX_AGE_PATTERN;
    private final JsonFactory jsonFactory;
    private List<PublicKey> publicKeys;
    private long expirationTimeMilliseconds;
    private final HttpTransport transport;
    private final Lock lock;
    private final Clock clock;
    private final String publicCertsEncodedUrl;
    
    public GooglePublicKeysManager(final HttpTransport a1, final JsonFactory a2) {
        this(new Builder(a1, a2));
    }
    
    protected GooglePublicKeysManager(final Builder a1) {
        super();
        this.lock = new ReentrantLock();
        this.transport = a1.transport;
        this.jsonFactory = a1.jsonFactory;
        this.clock = a1.clock;
        this.publicCertsEncodedUrl = a1.publicCertsEncodedUrl;
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
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        this.lock.lock();
        try {
            if (this.publicKeys == null || this.clock.currentTimeMillis() + 300000L > this.expirationTimeMilliseconds) {
                this.refresh();
            }
            return this.publicKeys;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public final long getExpirationTimeMilliseconds() {
        return this.expirationTimeMilliseconds;
    }
    
    public GooglePublicKeysManager refresh() throws GeneralSecurityException, IOException {
        this.lock.lock();
        try {
            this.publicKeys = new ArrayList<PublicKey>();
            final CertificateFactory x509CertificateFactory = SecurityUtils.getX509CertificateFactory();
            final HttpResponse execute = this.transport.createRequestFactory().buildGetRequest(new GenericUrl(this.publicCertsEncodedUrl)).execute();
            this.expirationTimeMilliseconds = this.clock.currentTimeMillis() + this.getCacheTimeInSec(execute.getHeaders()) * 1000L;
            final JsonParser jsonParser = this.jsonFactory.createJsonParser(execute.getContent());
            JsonToken v0 = jsonParser.getCurrentToken();
            if (v0 == null) {
                v0 = jsonParser.nextToken();
            }
            Preconditions.checkArgument(v0 == JsonToken.START_OBJECT);
            try {
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    jsonParser.nextToken();
                    final String v2 = jsonParser.getText();
                    final X509Certificate v3 = (X509Certificate)x509CertificateFactory.generateCertificate(new ByteArrayInputStream(StringUtils.getBytesUtf8(v2)));
                    this.publicKeys.add(v3.getPublicKey());
                }
                this.publicKeys = Collections.unmodifiableList((List<? extends PublicKey>)this.publicKeys);
            }
            finally {
                jsonParser.close();
            }
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    long getCacheTimeInSec(final HttpHeaders v-5) {
        long longValue = 0L;
        if (v-5.getCacheControl() != null) {
            for (final String v2 : v-5.getCacheControl().split(",")) {
                final Matcher a1 = GooglePublicKeysManager.MAX_AGE_PATTERN.matcher(v2);
                if (a1.matches()) {
                    longValue = Long.valueOf(a1.group(1));
                    break;
                }
            }
        }
        if (v-5.getAge() != null) {
            longValue -= v-5.getAge();
        }
        return Math.max(0L, longValue);
    }
    
    static {
        MAX_AGE_PATTERN = Pattern.compile("\\s*max-age\\s*=\\s*(\\d+)\\s*");
    }
    
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
}
