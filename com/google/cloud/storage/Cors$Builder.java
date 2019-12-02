package com.google.cloud.storage;

import com.google.common.collect.*;

public static final class Builder
{
    private Integer maxAgeSeconds;
    private ImmutableList<HttpMethod> methods;
    private ImmutableList<Origin> origins;
    private ImmutableList<String> responseHeaders;
    
    private Builder() {
        super();
    }
    
    public Builder setMaxAgeSeconds(final Integer a1) {
        this.maxAgeSeconds = a1;
        return this;
    }
    
    public Builder setMethods(final Iterable<HttpMethod> a1) {
        this.methods = ((a1 != null) ? ImmutableList.copyOf((Iterable<? extends HttpMethod>)a1) : null);
        return this;
    }
    
    public Builder setOrigins(final Iterable<Origin> a1) {
        this.origins = ((a1 != null) ? ImmutableList.copyOf((Iterable<? extends Origin>)a1) : null);
        return this;
    }
    
    public Builder setResponseHeaders(final Iterable<String> a1) {
        this.responseHeaders = ((a1 != null) ? ImmutableList.copyOf((Iterable<? extends String>)a1) : null);
        return this;
    }
    
    public Cors build() {
        return new Cors(this, null);
    }
    
    static /* synthetic */ Integer access$100(final Builder a1) {
        return a1.maxAgeSeconds;
    }
    
    static /* synthetic */ ImmutableList access$200(final Builder a1) {
        return a1.methods;
    }
    
    static /* synthetic */ ImmutableList access$300(final Builder a1) {
        return a1.origins;
    }
    
    static /* synthetic */ ImmutableList access$400(final Builder a1) {
        return a1.responseHeaders;
    }
    
    Builder(final Cors$1 a1) {
        this();
    }
}
