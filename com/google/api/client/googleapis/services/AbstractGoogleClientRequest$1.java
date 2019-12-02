package com.google.api.client.googleapis.services;

import com.google.api.client.http.*;
import java.io.*;

class AbstractGoogleClientRequest$1 implements HttpResponseInterceptor {
    final /* synthetic */ HttpResponseInterceptor val$responseInterceptor;
    final /* synthetic */ HttpRequest val$httpRequest;
    final /* synthetic */ AbstractGoogleClientRequest this$0;
    
    AbstractGoogleClientRequest$1(final AbstractGoogleClientRequest this$0, final HttpResponseInterceptor val$responseInterceptor, final HttpRequest val$httpRequest) {
        this.this$0 = this$0;
        this.val$responseInterceptor = val$responseInterceptor;
        this.val$httpRequest = val$httpRequest;
        super();
    }
    
    public void interceptResponse(final HttpResponse a1) throws IOException {
        if (this.val$responseInterceptor != null) {
            this.val$responseInterceptor.interceptResponse(a1);
        }
        if (!a1.isSuccessStatusCode() && this.val$httpRequest.getThrowExceptionOnExecuteError()) {
            throw this.this$0.newExceptionOnError(a1);
        }
    }
}