package com.google.api.client.googleapis;

import com.google.api.client.http.*;
import java.io.*;

public final class MethodOverride implements HttpExecuteInterceptor, HttpRequestInitializer
{
    public static final String HEADER = "X-HTTP-Method-Override";
    static final int MAX_URL_LENGTH = 2048;
    private final boolean overrideAllMethods;
    
    public MethodOverride() {
        this(false);
    }
    
    MethodOverride(final boolean a1) {
        super();
        this.overrideAllMethods = a1;
    }
    
    public void initialize(final HttpRequest a1) {
        a1.setInterceptor(this);
    }
    
    public void intercept(final HttpRequest v2) throws IOException {
        if (this.overrideThisMethod(v2)) {
            final String a1 = v2.getRequestMethod();
            v2.setRequestMethod("POST");
            v2.getHeaders().set("X-HTTP-Method-Override", a1);
            if (a1.equals("GET")) {
                v2.setContent(new UrlEncodedContent(v2.getUrl().clone()));
                v2.getUrl().clear();
            }
            else if (v2.getContent() == null) {
                v2.setContent(new EmptyContent());
            }
        }
    }
    
    private boolean overrideThisMethod(final HttpRequest a1) throws IOException {
        final String v1 = a1.getRequestMethod();
        if (v1.equals("POST")) {
            return false;
        }
        if (v1.equals("GET")) {
            if (a1.getUrl().build().length() <= 2048) {
                return !a1.getTransport().supportsMethod(v1);
            }
        }
        else if (!this.overrideAllMethods) {
            return !a1.getTransport().supportsMethod(v1);
        }
        return true;
    }
    
    public static final class Builder
    {
        private boolean overrideAllMethods;
        
        public Builder() {
            super();
        }
        
        public MethodOverride build() {
            return new MethodOverride(this.overrideAllMethods);
        }
        
        public boolean getOverrideAllMethods() {
            return this.overrideAllMethods;
        }
        
        public Builder setOverrideAllMethods(final boolean a1) {
            this.overrideAllMethods = a1;
            return this;
        }
    }
}
