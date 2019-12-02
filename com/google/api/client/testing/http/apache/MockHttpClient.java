package com.google.api.client.testing.http.apache;

import org.apache.http.impl.client.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.params.*;
import org.apache.http.client.*;
import org.apache.http.protocol.*;
import org.apache.http.message.*;
import org.apache.http.*;
import java.io.*;
import com.google.api.client.util.*;

@Beta
public class MockHttpClient extends DefaultHttpClient
{
    int responseCode;
    
    public MockHttpClient() {
        super();
    }
    
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor a1, final ClientConnectionManager a2, final ConnectionReuseStrategy a3, final ConnectionKeepAliveStrategy a4, final HttpRoutePlanner a5, final HttpProcessor a6, final HttpRequestRetryHandler a7, final RedirectHandler a8, final AuthenticationHandler a9, final AuthenticationHandler a10, final UserTokenHandler a11, final HttpParams a12) {
        return (RequestDirector)new RequestDirector() {
            final /* synthetic */ MockHttpClient this$0;
            
            MockHttpClient$1() {
                this.this$0 = a1;
                super();
            }
            
            @Beta
            public HttpResponse execute(final HttpHost a1, final HttpRequest a2, final HttpContext a3) throws HttpException, IOException {
                return (HttpResponse)new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, this.this$0.responseCode, (String)null);
            }
        };
    }
    
    public final int getResponseCode() {
        return this.responseCode;
    }
    
    public MockHttpClient setResponseCode(final int a1) {
        Preconditions.checkArgument(a1 >= 0);
        this.responseCode = a1;
        return this;
    }
}
