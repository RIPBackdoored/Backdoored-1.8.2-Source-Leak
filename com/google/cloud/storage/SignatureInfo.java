package com.google.cloud.storage;

import java.net.*;
import com.google.common.collect.*;
import java.text.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import com.google.common.net.*;
import com.google.common.base.*;
import java.util.*;

public class SignatureInfo
{
    public static final char COMPONENT_SEPARATOR = '\n';
    public static final String GOOG4_RSA_SHA256 = "GOOG4-RSA-SHA256";
    public static final String SCOPE = "/auto/storage/goog4_request";
    private final HttpMethod httpVerb;
    private final String contentMd5;
    private final String contentType;
    private final long expiration;
    private final Map<String, String> canonicalizedExtensionHeaders;
    private final URI canonicalizedResource;
    private final Storage.SignUrlOption.SignatureVersion signatureVersion;
    private final String accountEmail;
    private final long timestamp;
    private final String yearMonthDay;
    private final String exactDate;
    
    private SignatureInfo(final Builder a1) {
        super();
        this.httpVerb = a1.httpVerb;
        this.contentMd5 = a1.contentMd5;
        this.contentType = a1.contentType;
        this.expiration = a1.expiration;
        this.canonicalizedResource = a1.canonicalizedResource;
        this.signatureVersion = a1.signatureVersion;
        this.accountEmail = a1.accountEmail;
        this.timestamp = a1.timestamp;
        if (Storage.SignUrlOption.SignatureVersion.V4.equals(this.signatureVersion) && !a1.canonicalizedExtensionHeaders.containsKey("host")) {
            this.canonicalizedExtensionHeaders = (Map<String, String>)new ImmutableMap.Builder().putAll(a1.canonicalizedExtensionHeaders).put((Object)"host", (Object)"storage.googleapis.com").build();
        }
        else {
            this.canonicalizedExtensionHeaders = a1.canonicalizedExtensionHeaders;
        }
        final Date v1 = new Date(this.timestamp);
        final SimpleDateFormat v2 = new SimpleDateFormat("yyyyMMdd");
        final SimpleDateFormat v3 = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        v2.setTimeZone(TimeZone.getTimeZone("UTC"));
        v3.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.yearMonthDay = v2.format(v1);
        this.exactDate = v3.format(v1);
    }
    
    public String constructUnsignedPayload() {
        if (Storage.SignUrlOption.SignatureVersion.V4.equals(this.signatureVersion)) {
            return this.constructV4UnsignedPayload();
        }
        return this.constructV2UnsignedPayload();
    }
    
    private String constructV2UnsignedPayload() {
        final StringBuilder v1 = new StringBuilder();
        v1.append(this.httpVerb.name()).append('\n');
        if (this.contentMd5 != null) {
            v1.append(this.contentMd5);
        }
        v1.append('\n');
        if (this.contentType != null) {
            v1.append(this.contentType);
        }
        v1.append('\n');
        v1.append(this.expiration).append('\n');
        if (this.canonicalizedExtensionHeaders != null) {
            v1.append((CharSequence)new CanonicalExtensionHeadersSerializer(Storage.SignUrlOption.SignatureVersion.V2).serialize(this.canonicalizedExtensionHeaders));
        }
        v1.append(this.canonicalizedResource);
        return v1.toString();
    }
    
    private String constructV4UnsignedPayload() {
        final StringBuilder v1 = new StringBuilder();
        v1.append("GOOG4-RSA-SHA256").append('\n');
        v1.append(this.exactDate).append('\n');
        v1.append(this.yearMonthDay).append("/auto/storage/goog4_request").append('\n');
        v1.append(this.constructV4CanonicalRequestHash());
        return v1.toString();
    }
    
    private String constructV4CanonicalRequestHash() {
        final StringBuilder v1 = new StringBuilder();
        final CanonicalExtensionHeadersSerializer v2 = new CanonicalExtensionHeadersSerializer(Storage.SignUrlOption.SignatureVersion.V4);
        v1.append(this.httpVerb.name()).append('\n');
        v1.append(this.canonicalizedResource).append('\n');
        v1.append(this.constructV4QueryString()).append('\n');
        v1.append((CharSequence)v2.serialize(this.canonicalizedExtensionHeaders)).append('\n');
        v1.append((CharSequence)v2.serializeHeaderNames(this.canonicalizedExtensionHeaders)).append('\n');
        v1.append("UNSIGNED-PAYLOAD");
        return Hashing.sha256().hashString((CharSequence)v1.toString(), StandardCharsets.UTF_8).toString();
    }
    
    public String constructV4QueryString() {
        final StringBuilder v1 = new CanonicalExtensionHeadersSerializer(Storage.SignUrlOption.SignatureVersion.V4).serializeHeaderNames(this.canonicalizedExtensionHeaders);
        final StringBuilder v2 = new StringBuilder();
        v2.append("X-Goog-Algorithm=").append("GOOG4-RSA-SHA256").append("&");
        v2.append("X-Goog-Credential=" + UrlEscapers.urlFormParameterEscaper().escape(this.accountEmail + "/" + this.yearMonthDay + "/auto/storage/goog4_request") + "&");
        v2.append("X-Goog-Date=" + this.exactDate + "&");
        v2.append("X-Goog-Expires=" + this.expiration + "&");
        v2.append("X-Goog-SignedHeaders=" + UrlEscapers.urlFormParameterEscaper().escape(v1.toString()));
        return v2.toString();
    }
    
    public HttpMethod getHttpVerb() {
        return this.httpVerb;
    }
    
    public String getContentMd5() {
        return this.contentMd5;
    }
    
    public String getContentType() {
        return this.contentType;
    }
    
    public long getExpiration() {
        return this.expiration;
    }
    
    public Map<String, String> getCanonicalizedExtensionHeaders() {
        return this.canonicalizedExtensionHeaders;
    }
    
    public URI getCanonicalizedResource() {
        return this.canonicalizedResource;
    }
    
    public Storage.SignUrlOption.SignatureVersion getSignatureVersion() {
        return this.signatureVersion;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public String getAccountEmail() {
        return this.accountEmail;
    }
    
    static /* synthetic */ HttpMethod access$900(final SignatureInfo a1) {
        return a1.httpVerb;
    }
    
    static /* synthetic */ String access$1000(final SignatureInfo a1) {
        return a1.contentMd5;
    }
    
    static /* synthetic */ String access$1100(final SignatureInfo a1) {
        return a1.contentType;
    }
    
    static /* synthetic */ long access$1200(final SignatureInfo a1) {
        return a1.expiration;
    }
    
    static /* synthetic */ Map access$1300(final SignatureInfo a1) {
        return a1.canonicalizedExtensionHeaders;
    }
    
    static /* synthetic */ URI access$1400(final SignatureInfo a1) {
        return a1.canonicalizedResource;
    }
    
    static /* synthetic */ Storage.SignUrlOption.SignatureVersion access$1500(final SignatureInfo a1) {
        return a1.signatureVersion;
    }
    
    static /* synthetic */ String access$1600(final SignatureInfo a1) {
        return a1.accountEmail;
    }
    
    static /* synthetic */ long access$1700(final SignatureInfo a1) {
        return a1.timestamp;
    }
    
    SignatureInfo(final Builder a1, final SignatureInfo$1 a2) {
        this(a1);
    }
    
    public static final class Builder
    {
        private final HttpMethod httpVerb;
        private String contentMd5;
        private String contentType;
        private final long expiration;
        private Map<String, String> canonicalizedExtensionHeaders;
        private final URI canonicalizedResource;
        private Storage.SignUrlOption.SignatureVersion signatureVersion;
        private String accountEmail;
        private long timestamp;
        
        public Builder(final HttpMethod a1, final long a2, final URI a3) {
            super();
            this.httpVerb = a1;
            this.expiration = a2;
            this.canonicalizedResource = a3;
        }
        
        public Builder(final SignatureInfo a1) {
            super();
            this.httpVerb = a1.httpVerb;
            this.contentMd5 = a1.contentMd5;
            this.contentType = a1.contentType;
            this.expiration = a1.expiration;
            this.canonicalizedExtensionHeaders = a1.canonicalizedExtensionHeaders;
            this.canonicalizedResource = a1.canonicalizedResource;
            this.signatureVersion = a1.signatureVersion;
            this.accountEmail = a1.accountEmail;
            this.timestamp = a1.timestamp;
        }
        
        public Builder setContentMd5(final String a1) {
            this.contentMd5 = a1;
            return this;
        }
        
        public Builder setContentType(final String a1) {
            this.contentType = a1;
            return this;
        }
        
        public Builder setCanonicalizedExtensionHeaders(final Map<String, String> a1) {
            this.canonicalizedExtensionHeaders = a1;
            return this;
        }
        
        public Builder setSignatureVersion(final Storage.SignUrlOption.SignatureVersion a1) {
            this.signatureVersion = a1;
            return this;
        }
        
        public Builder setAccountEmail(final String a1) {
            this.accountEmail = a1;
            return this;
        }
        
        public Builder setTimestamp(final long a1) {
            this.timestamp = a1;
            return this;
        }
        
        public SignatureInfo build() {
            Preconditions.checkArgument(this.httpVerb != null, (Object)"Required HTTP method");
            Preconditions.checkArgument(this.canonicalizedResource != null, (Object)"Required canonicalized resource");
            Preconditions.checkArgument(this.expiration >= 0L, (Object)"Expiration must be greater than or equal to zero");
            if (Storage.SignUrlOption.SignatureVersion.V4.equals(this.signatureVersion)) {
                Preconditions.checkArgument(this.accountEmail != null, (Object)"Account email required to use V4 signing");
                Preconditions.checkArgument(this.timestamp > 0L, (Object)"Timestamp required to use V4 signing");
                Preconditions.checkArgument(this.expiration <= 604800L, (Object)"Expiration can't be longer than 7 days to use V4 signing");
            }
            if (this.canonicalizedExtensionHeaders == null) {
                this.canonicalizedExtensionHeaders = new HashMap<String, String>();
            }
            return new SignatureInfo(this, null);
        }
        
        static /* synthetic */ HttpMethod access$000(final Builder a1) {
            return a1.httpVerb;
        }
        
        static /* synthetic */ String access$100(final Builder a1) {
            return a1.contentMd5;
        }
        
        static /* synthetic */ String access$200(final Builder a1) {
            return a1.contentType;
        }
        
        static /* synthetic */ long access$300(final Builder a1) {
            return a1.expiration;
        }
        
        static /* synthetic */ URI access$400(final Builder a1) {
            return a1.canonicalizedResource;
        }
        
        static /* synthetic */ Storage.SignUrlOption.SignatureVersion access$500(final Builder a1) {
            return a1.signatureVersion;
        }
        
        static /* synthetic */ String access$600(final Builder a1) {
            return a1.accountEmail;
        }
        
        static /* synthetic */ long access$700(final Builder a1) {
            return a1.timestamp;
        }
        
        static /* synthetic */ Map access$800(final Builder a1) {
            return a1.canonicalizedExtensionHeaders;
        }
    }
}
