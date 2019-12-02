package com.google.api.client.http.apache;

import org.apache.http.client.methods.*;
import com.google.api.client.util.*;
import java.net.*;

final class HttpExtensionMethod extends HttpEntityEnclosingRequestBase
{
    private final String methodName;
    
    public HttpExtensionMethod(final String a1, final String a2) {
        super();
        this.methodName = Preconditions.checkNotNull(a1);
        this.setURI(URI.create(a2));
    }
    
    public String getMethod() {
        return this.methodName;
    }
}
