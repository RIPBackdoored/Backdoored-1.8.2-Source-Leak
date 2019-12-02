package com.google.api.client.testing.http.apache;

import org.apache.http.client.*;
import org.apache.http.protocol.*;
import org.apache.http.message.*;
import org.apache.http.*;
import java.io.*;
import com.google.api.client.util.*;

class MockHttpClient$1 implements RequestDirector {
    final /* synthetic */ MockHttpClient this$0;
    
    MockHttpClient$1(final MockHttpClient a1) {
        this.this$0 = a1;
        super();
    }
    
    @Beta
    public HttpResponse execute(final HttpHost a1, final HttpRequest a2, final HttpContext a3) throws HttpException, IOException {
        return (HttpResponse)new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, this.this$0.responseCode, (String)null);
    }
}