package com.google.api.client.testing.http;

import java.util.*;
import com.google.api.client.util.*;

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
