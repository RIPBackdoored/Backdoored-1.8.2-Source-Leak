package com.google.api.client.googleapis.batch;

import com.google.api.client.util.*;
import java.util.*;
import com.google.api.client.http.*;
import java.io.*;

public final class BatchRequest
{
    private GenericUrl batchUrl;
    private final HttpRequestFactory requestFactory;
    List<RequestInfo<?, ?>> requestInfos;
    private Sleeper sleeper;
    
    public BatchRequest(final HttpTransport a1, final HttpRequestInitializer a2) {
        super();
        this.batchUrl = new GenericUrl("https://www.googleapis.com/batch");
        this.requestInfos = new ArrayList<RequestInfo<?, ?>>();
        this.sleeper = Sleeper.DEFAULT;
        this.requestFactory = ((a2 == null) ? a1.createRequestFactory() : a1.createRequestFactory(a2));
    }
    
    public BatchRequest setBatchUrl(final GenericUrl a1) {
        this.batchUrl = a1;
        return this;
    }
    
    public GenericUrl getBatchUrl() {
        return this.batchUrl;
    }
    
    public Sleeper getSleeper() {
        return this.sleeper;
    }
    
    public BatchRequest setSleeper(final Sleeper a1) {
        this.sleeper = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public <T, E> BatchRequest queue(final HttpRequest a1, final Class<T> a2, final Class<E> a3, final BatchCallback<T, E> a4) throws IOException {
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a4);
        Preconditions.checkNotNull(a2);
        Preconditions.checkNotNull(a3);
        this.requestInfos.add(new RequestInfo<Object, Object>((BatchCallback<Object, Object>)a4, (Class<Object>)a2, (Class<Object>)a3, a1));
        return this;
    }
    
    public int size() {
        return this.requestInfos.size();
    }
    
    public void execute() throws IOException {
        Preconditions.checkState(!this.requestInfos.isEmpty());
        final HttpRequest buildPostRequest = this.requestFactory.buildPostRequest(this.batchUrl, null);
        final HttpExecuteInterceptor interceptor = buildPostRequest.getInterceptor();
        buildPostRequest.setInterceptor(new BatchInterceptor(interceptor));
        int numberOfRetries = buildPostRequest.getNumberOfRetries();
        final BackOffPolicy backOffPolicy = buildPostRequest.getBackOffPolicy();
        if (backOffPolicy != null) {
            backOffPolicy.reset();
        }
        boolean a4;
        do {
            a4 = (numberOfRetries > 0);
            final MultipartContent content = new MultipartContent();
            content.getMediaType().setSubType("mixed");
            int n = 1;
            for (final RequestInfo<?, ?> v2 : this.requestInfos) {
                content.addPart(new MultipartContent.Part(new HttpHeaders().setAcceptEncoding(null).set("Content-ID", n++), new HttpRequestContent(v2.request)));
            }
            buildPostRequest.setContent(content);
            final HttpResponse v3 = buildPostRequest.execute();
            BatchUnparsedResponse v6;
            try {
                final String s = "--";
                final String value = String.valueOf(v3.getMediaType().getParameter("boundary"));
                final String v4 = (value.length() != 0) ? s.concat(value) : new String(s);
                final InputStream v5 = v3.getContent();
                v6 = new BatchUnparsedResponse(v5, v4, this.requestInfos, a4);
                while (v6.hasNext) {
                    v6.parseNextResponse();
                }
            }
            finally {
                v3.disconnect();
            }
            final List<RequestInfo<?, ?>> v7 = v6.unsuccessfulRequestInfos;
            if (v7.isEmpty()) {
                break;
            }
            this.requestInfos = v7;
            if (v6.backOffRequired && backOffPolicy != null) {
                final long v8 = backOffPolicy.getNextBackOffMillis();
                if (v8 != -1L) {
                    try {
                        this.sleeper.sleep(v8);
                    }
                    catch (InterruptedException ex) {}
                }
            }
            --numberOfRetries;
        } while (a4);
        this.requestInfos.clear();
    }
    
    static class RequestInfo<T, E>
    {
        final BatchCallback<T, E> callback;
        final Class<T> dataClass;
        final Class<E> errorClass;
        final HttpRequest request;
        
        RequestInfo(final BatchCallback<T, E> a1, final Class<T> a2, final Class<E> a3, final HttpRequest a4) {
            super();
            this.callback = a1;
            this.dataClass = a2;
            this.errorClass = a3;
            this.request = a4;
        }
    }
    
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
}
