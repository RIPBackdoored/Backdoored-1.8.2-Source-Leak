package com.google.api.client.testing.http;

import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.util.*;

@Beta
public class MockHttpTransport extends HttpTransport
{
    private Set<String> supportedMethods;
    private MockLowLevelHttpRequest lowLevelHttpRequest;
    private MockLowLevelHttpResponse lowLevelHttpResponse;
    
    public MockHttpTransport() {
        super();
    }
    
    protected MockHttpTransport(final Builder a1) {
        super();
        this.supportedMethods = a1.supportedMethods;
        this.lowLevelHttpRequest = a1.lowLevelHttpRequest;
        this.lowLevelHttpResponse = a1.lowLevelHttpResponse;
    }
    
    @Override
    public boolean supportsMethod(final String a1) throws IOException {
        return this.supportedMethods == null || this.supportedMethods.contains(a1);
    }
    
    public LowLevelHttpRequest buildRequest(final String a1, final String a2) throws IOException {
        Preconditions.checkArgument(this.supportsMethod(a1), "HTTP method %s not supported", a1);
        if (this.lowLevelHttpRequest != null) {
            return this.lowLevelHttpRequest;
        }
        this.lowLevelHttpRequest = new MockLowLevelHttpRequest(a2);
        if (this.lowLevelHttpResponse != null) {
            this.lowLevelHttpRequest.setResponse(this.lowLevelHttpResponse);
        }
        return this.lowLevelHttpRequest;
    }
    
    public final Set<String> getSupportedMethods() {
        return (this.supportedMethods == null) ? null : Collections.unmodifiableSet((Set<? extends String>)this.supportedMethods);
    }
    
    public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
        return this.lowLevelHttpRequest;
    }
    
    @Deprecated
    public static Builder builder() {
        return new Builder();
    }
    
    @Beta
    public static class Builder
    {
        Set<String> supportedMethods;
        MockLowLevelHttpRequest lowLevelHttpRequest;
        MockLowLevelHttpResponse lowLevelHttpResponse;
        
        public Builder() {
            super();
        }
        
        public MockHttpTransport build() {
            return new MockHttpTransport(this);
        }
        
        public final Set<String> getSupportedMethods() {
            return this.supportedMethods;
        }
        
        public final Builder setSupportedMethods(final Set<String> a1) {
            this.supportedMethods = a1;
            return this;
        }
        
        public final Builder setLowLevelHttpRequest(final MockLowLevelHttpRequest a1) {
            Preconditions.checkState(this.lowLevelHttpResponse == null, (Object)"Cannnot set a low level HTTP request when a low level HTTP response has been set.");
            this.lowLevelHttpRequest = a1;
            return this;
        }
        
        public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
            return this.lowLevelHttpRequest;
        }
        
        public final Builder setLowLevelHttpResponse(final MockLowLevelHttpResponse a1) {
            Preconditions.checkState(this.lowLevelHttpRequest == null, (Object)"Cannot set a low level HTTP response when a low level HTTP request has been set.");
            this.lowLevelHttpResponse = a1;
            return this;
        }
        
        MockLowLevelHttpResponse getLowLevelHttpResponse() {
            return this.lowLevelHttpResponse;
        }
    }
}
