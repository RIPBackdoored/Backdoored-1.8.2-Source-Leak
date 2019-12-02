package com.google.cloud.storage;

import java.net.*;
import com.google.common.base.*;
import java.util.*;

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
        this.httpVerb = SignatureInfo.access$900(a1);
        this.contentMd5 = SignatureInfo.access$1000(a1);
        this.contentType = SignatureInfo.access$1100(a1);
        this.expiration = SignatureInfo.access$1200(a1);
        this.canonicalizedExtensionHeaders = (Map<String, String>)SignatureInfo.access$1300(a1);
        this.canonicalizedResource = SignatureInfo.access$1400(a1);
        this.signatureVersion = SignatureInfo.access$1500(a1);
        this.accountEmail = SignatureInfo.access$1600(a1);
        this.timestamp = SignatureInfo.access$1700(a1);
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
