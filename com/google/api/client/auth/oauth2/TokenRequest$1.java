package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;

class TokenRequest$1 implements HttpRequestInitializer {
    final /* synthetic */ TokenRequest this$0;
    
    TokenRequest$1(final TokenRequest a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public void initialize(final HttpRequest a1) throws IOException {
        if (this.this$0.requestInitializer != null) {
            this.this$0.requestInitializer.initialize(a1);
        }
        final HttpExecuteInterceptor v1 = a1.getInterceptor();
        a1.setInterceptor(new HttpExecuteInterceptor() {
            final /* synthetic */ HttpExecuteInterceptor val$interceptor;
            final /* synthetic */ TokenRequest$1 this$1;
            
            TokenRequest$1$1() {
                this.this$1 = a1;
                super();
            }
            
            @Override
            public void intercept(final HttpRequest a1) throws IOException {
                if (v1 != null) {
                    v1.intercept(a1);
                }
                if (this.this$1.this$0.clientAuthentication != null) {
                    this.this$1.this$0.clientAuthentication.intercept(a1);
                }
            }
        });
    }
}