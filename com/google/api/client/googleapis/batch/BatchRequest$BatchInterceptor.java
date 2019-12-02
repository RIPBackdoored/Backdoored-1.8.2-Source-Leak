package com.google.api.client.googleapis.batch;

import com.google.api.client.http.*;
import java.util.*;
import java.io.*;

class BatchInterceptor implements HttpExecuteInterceptor
{
    private HttpExecuteInterceptor originalInterceptor;
    final /* synthetic */ BatchRequest this$0;
    
    BatchInterceptor(final BatchRequest this$0, final HttpExecuteInterceptor a1) {
        this.this$0 = this$0;
        super();
        this.originalInterceptor = a1;
    }
    
    public void intercept(final HttpRequest v-1) throws IOException {
        if (this.originalInterceptor != null) {
            this.originalInterceptor.intercept(v-1);
        }
        for (final RequestInfo<?, ?> v2 : this.this$0.requestInfos) {
            final HttpExecuteInterceptor a1 = v2.request.getInterceptor();
            if (a1 != null) {
                a1.intercept(v2.request);
            }
        }
    }
}
