package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;

class TokenRequest$1$1 implements HttpExecuteInterceptor {
    final /* synthetic */ HttpExecuteInterceptor val$interceptor;
    final /* synthetic */ TokenRequest$1 this$1;
    
    TokenRequest$1$1(final TokenRequest$1 a1, final HttpExecuteInterceptor val$interceptor) {
        this.this$1 = a1;
        this.val$interceptor = val$interceptor;
        super();
    }
    
    @Override
    public void intercept(final HttpRequest a1) throws IOException {
        if (this.val$interceptor != null) {
            this.val$interceptor.intercept(a1);
        }
        if (this.this$1.this$0.clientAuthentication != null) {
            this.this$1.this$0.clientAuthentication.intercept(a1);
        }
    }
}