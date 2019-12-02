package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.media.*;
import com.google.api.client.util.*;
import com.google.api.client.googleapis.*;
import java.util.*;
import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.googleapis.batch.*;

public abstract class AbstractGoogleClientRequest<T> extends GenericData
{
    public static final String USER_AGENT_SUFFIX = "Google-API-Java-Client";
    private final AbstractGoogleClient abstractGoogleClient;
    private final String requestMethod;
    private final String uriTemplate;
    private final HttpContent httpContent;
    private HttpHeaders requestHeaders;
    private HttpHeaders lastResponseHeaders;
    private int lastStatusCode;
    private String lastStatusMessage;
    private boolean disableGZipContent;
    private Class<T> responseClass;
    private MediaHttpUploader uploader;
    private MediaHttpDownloader downloader;
    
    protected AbstractGoogleClientRequest(final AbstractGoogleClient a1, final String a2, final String a3, final HttpContent a4, final Class<T> a5) {
        super();
        this.requestHeaders = new HttpHeaders();
        this.lastStatusCode = -1;
        this.responseClass = Preconditions.checkNotNull(a5);
        this.abstractGoogleClient = Preconditions.checkNotNull(a1);
        this.requestMethod = Preconditions.checkNotNull(a2);
        this.uriTemplate = Preconditions.checkNotNull(a3);
        this.httpContent = a4;
        final String v1 = a1.getApplicationName();
        if (v1 != null) {
            final HttpHeaders requestHeaders = this.requestHeaders;
            final String value = String.valueOf(String.valueOf(v1));
            final String value2 = String.valueOf(String.valueOf("Google-API-Java-Client"));
            requestHeaders.setUserAgent(new StringBuilder(1 + value.length() + value2.length()).append(value).append(" ").append(value2).toString());
        }
        else {
            this.requestHeaders.setUserAgent("Google-API-Java-Client");
        }
    }
    
    public final boolean getDisableGZipContent() {
        return this.disableGZipContent;
    }
    
    public AbstractGoogleClientRequest<T> setDisableGZipContent(final boolean a1) {
        this.disableGZipContent = a1;
        return this;
    }
    
    public final String getRequestMethod() {
        return this.requestMethod;
    }
    
    public final String getUriTemplate() {
        return this.uriTemplate;
    }
    
    public final HttpContent getHttpContent() {
        return this.httpContent;
    }
    
    public AbstractGoogleClient getAbstractGoogleClient() {
        return this.abstractGoogleClient;
    }
    
    public final HttpHeaders getRequestHeaders() {
        return this.requestHeaders;
    }
    
    public AbstractGoogleClientRequest<T> setRequestHeaders(final HttpHeaders a1) {
        this.requestHeaders = a1;
        return this;
    }
    
    public final HttpHeaders getLastResponseHeaders() {
        return this.lastResponseHeaders;
    }
    
    public final int getLastStatusCode() {
        return this.lastStatusCode;
    }
    
    public final String getLastStatusMessage() {
        return this.lastStatusMessage;
    }
    
    public final Class<T> getResponseClass() {
        return this.responseClass;
    }
    
    public final MediaHttpUploader getMediaHttpUploader() {
        return this.uploader;
    }
    
    protected final void initializeMediaUpload(final AbstractInputStreamContent a1) {
        final HttpRequestFactory v1 = this.abstractGoogleClient.getRequestFactory();
        (this.uploader = new MediaHttpUploader(a1, v1.getTransport(), v1.getInitializer())).setInitiationRequestMethod(this.requestMethod);
        if (this.httpContent != null) {
            this.uploader.setMetadata(this.httpContent);
        }
    }
    
    public final MediaHttpDownloader getMediaHttpDownloader() {
        return this.downloader;
    }
    
    protected final void initializeMediaDownload() {
        final HttpRequestFactory v1 = this.abstractGoogleClient.getRequestFactory();
        this.downloader = new MediaHttpDownloader(v1.getTransport(), v1.getInitializer());
    }
    
    public GenericUrl buildHttpRequestUrl() {
        return new GenericUrl(UriTemplate.expand(this.abstractGoogleClient.getBaseUrl(), this.uriTemplate, this, true));
    }
    
    public HttpRequest buildHttpRequest() throws IOException {
        return this.buildHttpRequest(false);
    }
    
    protected HttpRequest buildHttpRequestUsingHead() throws IOException {
        return this.buildHttpRequest(true);
    }
    
    private HttpRequest buildHttpRequest(final boolean a1) throws IOException {
        Preconditions.checkArgument(this.uploader == null);
        Preconditions.checkArgument(!a1 || this.requestMethod.equals("GET"));
        final String v1 = a1 ? "HEAD" : this.requestMethod;
        final HttpRequest v2 = this.getAbstractGoogleClient().getRequestFactory().buildRequest(v1, this.buildHttpRequestUrl(), this.httpContent);
        new MethodOverride().intercept(v2);
        v2.setParser(this.getAbstractGoogleClient().getObjectParser());
        if (this.httpContent == null && (this.requestMethod.equals("POST") || this.requestMethod.equals("PUT") || this.requestMethod.equals("PATCH"))) {
            v2.setContent(new EmptyContent());
        }
        v2.getHeaders().putAll(this.requestHeaders);
        if (!this.disableGZipContent) {
            v2.setEncoding(new GZipEncoding());
        }
        final HttpResponseInterceptor v3 = v2.getResponseInterceptor();
        v2.setResponseInterceptor(new HttpResponseInterceptor() {
            final /* synthetic */ HttpResponseInterceptor val$responseInterceptor;
            final /* synthetic */ HttpRequest val$httpRequest;
            final /* synthetic */ AbstractGoogleClientRequest this$0;
            
            AbstractGoogleClientRequest$1() {
                this.this$0 = this$0;
                super();
            }
            
            public void interceptResponse(final HttpResponse a1) throws IOException {
                if (v3 != null) {
                    v3.interceptResponse(a1);
                }
                if (!a1.isSuccessStatusCode() && v2.getThrowExceptionOnExecuteError()) {
                    throw this.this$0.newExceptionOnError(a1);
                }
            }
        });
        return v2;
    }
    
    public HttpResponse executeUnparsed() throws IOException {
        return this.executeUnparsed(false);
    }
    
    protected HttpResponse executeMedia() throws IOException {
        this.set("alt", "media");
        return this.executeUnparsed();
    }
    
    protected HttpResponse executeUsingHead() throws IOException {
        Preconditions.checkArgument(this.uploader == null);
        final HttpResponse v1 = this.executeUnparsed(true);
        v1.ignore();
        return v1;
    }
    
    private HttpResponse executeUnparsed(final boolean v-1) throws IOException {
        HttpResponse v4 = null;
        if (this.uploader == null) {
            final HttpResponse a1 = this.buildHttpRequest(v-1).execute();
        }
        else {
            final GenericUrl v1 = this.buildHttpRequestUrl();
            final HttpRequest v2 = this.getAbstractGoogleClient().getRequestFactory().buildRequest(this.requestMethod, v1, this.httpContent);
            final boolean v3 = v2.getThrowExceptionOnExecuteError();
            v4 = this.uploader.setInitiationHeaders(this.requestHeaders).setDisableGZipContent(this.disableGZipContent).upload(v1);
            v4.getRequest().setParser(this.getAbstractGoogleClient().getObjectParser());
            if (v3 && !v4.isSuccessStatusCode()) {
                throw this.newExceptionOnError(v4);
            }
        }
        this.lastResponseHeaders = v4.getHeaders();
        this.lastStatusCode = v4.getStatusCode();
        this.lastStatusMessage = v4.getStatusMessage();
        return v4;
    }
    
    protected IOException newExceptionOnError(final HttpResponse a1) {
        return new HttpResponseException(a1);
    }
    
    public T execute() throws IOException {
        return this.executeUnparsed().parseAs(this.responseClass);
    }
    
    public InputStream executeAsInputStream() throws IOException {
        return this.executeUnparsed().getContent();
    }
    
    protected InputStream executeMediaAsInputStream() throws IOException {
        return this.executeMedia().getContent();
    }
    
    public void executeAndDownloadTo(final OutputStream a1) throws IOException {
        this.executeUnparsed().download(a1);
    }
    
    protected void executeMediaAndDownloadTo(final OutputStream a1) throws IOException {
        if (this.downloader == null) {
            this.executeMedia().download(a1);
        }
        else {
            this.downloader.download(this.buildHttpRequestUrl(), this.requestHeaders, a1);
        }
    }
    
    public final <E> void queue(final BatchRequest a1, final Class<E> a2, final BatchCallback<T, E> a3) throws IOException {
        Preconditions.checkArgument(this.uploader == null, (Object)"Batching media requests is not supported");
        a1.queue(this.buildHttpRequest(), this.getResponseClass(), a2, a3);
    }
    
    @Override
    public AbstractGoogleClientRequest<T> set(final String a1, final Object a2) {
        return (AbstractGoogleClientRequest)super.set(a1, a2);
    }
    
    protected final void checkRequiredParameter(final Object a1, final String a2) {
        Preconditions.checkArgument(this.abstractGoogleClient.getSuppressRequiredParameterChecks() || a1 != null, "Required parameter %s must be specified", a2);
    }
    
    @Override
    public /* bridge */ GenericData set(final String a1, final Object a2) {
        return this.set(a1, a2);
    }
}
