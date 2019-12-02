package com.google.api.client.http;

import java.util.logging.*;
import com.google.api.client.util.*;
import java.io.*;
import java.util.concurrent.*;

public final class HttpRequest
{
    public static final String VERSION = "1.25.0";
    public static final String USER_AGENT_SUFFIX = "Google-HTTP-Java-Client/1.25.0 (gzip)";
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    private HttpExecuteInterceptor executeInterceptor;
    private HttpHeaders headers;
    private HttpHeaders responseHeaders;
    private int numRetries;
    private int contentLoggingLimit;
    private boolean loggingEnabled;
    private boolean curlLoggingEnabled;
    private HttpContent content;
    private final HttpTransport transport;
    private String requestMethod;
    private GenericUrl url;
    private int connectTimeout;
    private int readTimeout;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    @Beta
    private HttpIOExceptionHandler ioExceptionHandler;
    private HttpResponseInterceptor responseInterceptor;
    private ObjectParser objectParser;
    private HttpEncoding encoding;
    @Deprecated
    @Beta
    private BackOffPolicy backOffPolicy;
    private boolean followRedirects;
    private boolean throwExceptionOnExecuteError;
    @Deprecated
    @Beta
    private boolean retryOnExecuteIOException;
    private boolean suppressUserAgentSuffix;
    private Sleeper sleeper;
    
    HttpRequest(final HttpTransport a1, final String a2) {
        super();
        this.headers = new HttpHeaders();
        this.responseHeaders = new HttpHeaders();
        this.numRetries = 10;
        this.contentLoggingLimit = 16384;
        this.loggingEnabled = true;
        this.curlLoggingEnabled = true;
        this.connectTimeout = 20000;
        this.readTimeout = 20000;
        this.followRedirects = true;
        this.throwExceptionOnExecuteError = true;
        this.retryOnExecuteIOException = false;
        this.sleeper = Sleeper.DEFAULT;
        this.transport = a1;
        this.setRequestMethod(a2);
    }
    
    public HttpTransport getTransport() {
        return this.transport;
    }
    
    public String getRequestMethod() {
        return this.requestMethod;
    }
    
    public HttpRequest setRequestMethod(final String a1) {
        Preconditions.checkArgument(a1 == null || HttpMediaType.matchesToken(a1));
        this.requestMethod = a1;
        return this;
    }
    
    public GenericUrl getUrl() {
        return this.url;
    }
    
    public HttpRequest setUrl(final GenericUrl a1) {
        this.url = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public HttpContent getContent() {
        return this.content;
    }
    
    public HttpRequest setContent(final HttpContent a1) {
        this.content = a1;
        return this;
    }
    
    public HttpEncoding getEncoding() {
        return this.encoding;
    }
    
    public HttpRequest setEncoding(final HttpEncoding a1) {
        this.encoding = a1;
        return this;
    }
    
    @Deprecated
    @Beta
    public BackOffPolicy getBackOffPolicy() {
        return this.backOffPolicy;
    }
    
    @Deprecated
    @Beta
    public HttpRequest setBackOffPolicy(final BackOffPolicy a1) {
        this.backOffPolicy = a1;
        return this;
    }
    
    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }
    
    public HttpRequest setContentLoggingLimit(final int a1) {
        Preconditions.checkArgument(a1 >= 0, (Object)"The content logging limit must be non-negative.");
        this.contentLoggingLimit = a1;
        return this;
    }
    
    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }
    
    public HttpRequest setLoggingEnabled(final boolean a1) {
        this.loggingEnabled = a1;
        return this;
    }
    
    public boolean isCurlLoggingEnabled() {
        return this.curlLoggingEnabled;
    }
    
    public HttpRequest setCurlLoggingEnabled(final boolean a1) {
        this.curlLoggingEnabled = a1;
        return this;
    }
    
    public int getConnectTimeout() {
        return this.connectTimeout;
    }
    
    public HttpRequest setConnectTimeout(final int a1) {
        Preconditions.checkArgument(a1 >= 0);
        this.connectTimeout = a1;
        return this;
    }
    
    public int getReadTimeout() {
        return this.readTimeout;
    }
    
    public HttpRequest setReadTimeout(final int a1) {
        Preconditions.checkArgument(a1 >= 0);
        this.readTimeout = a1;
        return this;
    }
    
    public HttpHeaders getHeaders() {
        return this.headers;
    }
    
    public HttpRequest setHeaders(final HttpHeaders a1) {
        this.headers = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }
    
    public HttpRequest setResponseHeaders(final HttpHeaders a1) {
        this.responseHeaders = Preconditions.checkNotNull(a1);
        return this;
    }
    
    public HttpExecuteInterceptor getInterceptor() {
        return this.executeInterceptor;
    }
    
    public HttpRequest setInterceptor(final HttpExecuteInterceptor a1) {
        this.executeInterceptor = a1;
        return this;
    }
    
    public HttpUnsuccessfulResponseHandler getUnsuccessfulResponseHandler() {
        return this.unsuccessfulResponseHandler;
    }
    
    public HttpRequest setUnsuccessfulResponseHandler(final HttpUnsuccessfulResponseHandler a1) {
        this.unsuccessfulResponseHandler = a1;
        return this;
    }
    
    @Beta
    public HttpIOExceptionHandler getIOExceptionHandler() {
        return this.ioExceptionHandler;
    }
    
    @Beta
    public HttpRequest setIOExceptionHandler(final HttpIOExceptionHandler a1) {
        this.ioExceptionHandler = a1;
        return this;
    }
    
    public HttpResponseInterceptor getResponseInterceptor() {
        return this.responseInterceptor;
    }
    
    public HttpRequest setResponseInterceptor(final HttpResponseInterceptor a1) {
        this.responseInterceptor = a1;
        return this;
    }
    
    public int getNumberOfRetries() {
        return this.numRetries;
    }
    
    public HttpRequest setNumberOfRetries(final int a1) {
        Preconditions.checkArgument(a1 >= 0);
        this.numRetries = a1;
        return this;
    }
    
    public HttpRequest setParser(final ObjectParser a1) {
        this.objectParser = a1;
        return this;
    }
    
    public final ObjectParser getParser() {
        return this.objectParser;
    }
    
    public boolean getFollowRedirects() {
        return this.followRedirects;
    }
    
    public HttpRequest setFollowRedirects(final boolean a1) {
        this.followRedirects = a1;
        return this;
    }
    
    public boolean getThrowExceptionOnExecuteError() {
        return this.throwExceptionOnExecuteError;
    }
    
    public HttpRequest setThrowExceptionOnExecuteError(final boolean a1) {
        this.throwExceptionOnExecuteError = a1;
        return this;
    }
    
    @Deprecated
    @Beta
    public boolean getRetryOnExecuteIOException() {
        return this.retryOnExecuteIOException;
    }
    
    @Deprecated
    @Beta
    public HttpRequest setRetryOnExecuteIOException(final boolean a1) {
        this.retryOnExecuteIOException = a1;
        return this;
    }
    
    public boolean getSuppressUserAgentSuffix() {
        return this.suppressUserAgentSuffix;
    }
    
    public HttpRequest setSuppressUserAgentSuffix(final boolean a1) {
        this.suppressUserAgentSuffix = a1;
        return this;
    }
    
    public HttpResponse execute() throws IOException {
        boolean b = false;
        Preconditions.checkArgument(this.numRetries >= 0);
        int numRetries = this.numRetries;
        if (this.backOffPolicy != null) {
            this.backOffPolicy.reset();
        }
        HttpResponse a1 = null;
        Preconditions.checkNotNull(this.requestMethod);
        Preconditions.checkNotNull(this.url);
        IOException ex;
        do {
            if (a1 != null) {
                a1.ignore();
            }
            a1 = null;
            ex = null;
            if (this.executeInterceptor != null) {
                this.executeInterceptor.intercept(this);
            }
            final String build = this.url.build();
            final LowLevelHttpRequest buildRequest = this.transport.buildRequest(this.requestMethod, build);
            final Logger logger = HttpTransport.LOGGER;
            final boolean b2 = this.loggingEnabled && logger.isLoggable(Level.CONFIG);
            StringBuilder a2 = null;
            StringBuilder a3 = null;
            if (b2) {
                a2 = new StringBuilder();
                a2.append("-------------- REQUEST  --------------").append(StringUtils.LINE_SEPARATOR);
                a2.append(this.requestMethod).append(' ').append(build).append(StringUtils.LINE_SEPARATOR);
                if (this.curlLoggingEnabled) {
                    a3 = new StringBuilder("curl -v --compressed");
                    if (!this.requestMethod.equals("GET")) {
                        a3.append(" -X ").append(this.requestMethod);
                    }
                }
            }
            final String userAgent = this.headers.getUserAgent();
            if (!this.suppressUserAgentSuffix) {
                if (userAgent == null) {
                    this.headers.setUserAgent("Google-HTTP-Java-Client/1.25.0 (gzip)");
                }
                else {
                    this.headers.setUserAgent(userAgent + " " + "Google-HTTP-Java-Client/1.25.0 (gzip)");
                }
            }
            HttpHeaders.serializeHeaders(this.headers, a2, a3, logger, buildRequest);
            if (!this.suppressUserAgentSuffix) {
                this.headers.setUserAgent(userAgent);
            }
            StreamingContent content = this.content;
            final boolean b3 = content == null || this.content.retrySupported();
            if (content != null) {
                final String v0 = this.content.getType();
                if (b2) {
                    content = new LoggingStreamingContent(content, HttpTransport.LOGGER, Level.CONFIG, this.contentLoggingLimit);
                }
                String v2;
                long v3;
                if (this.encoding == null) {
                    v2 = null;
                    v3 = this.content.getLength();
                }
                else {
                    v2 = this.encoding.getName();
                    content = new HttpEncodingStreamingContent(content, this.encoding);
                    v3 = (b3 ? IOUtils.computeLength(content) : -1L);
                }
                if (b2) {
                    if (v0 != null) {
                        final String v4 = "Content-Type: " + v0;
                        a2.append(v4).append(StringUtils.LINE_SEPARATOR);
                        if (a3 != null) {
                            a3.append(" -H '" + v4 + "'");
                        }
                    }
                    if (v2 != null) {
                        final String v4 = "Content-Encoding: " + v2;
                        a2.append(v4).append(StringUtils.LINE_SEPARATOR);
                        if (a3 != null) {
                            a3.append(" -H '" + v4 + "'");
                        }
                    }
                    if (v3 >= 0L) {
                        final String v4 = "Content-Length: " + v3;
                        a2.append(v4).append(StringUtils.LINE_SEPARATOR);
                    }
                }
                if (a3 != null) {
                    a3.append(" -d '@-'");
                }
                buildRequest.setContentType(v0);
                buildRequest.setContentEncoding(v2);
                buildRequest.setContentLength(v3);
                buildRequest.setStreamingContent(content);
            }
            if (b2) {
                logger.config(a2.toString());
                if (a3 != null) {
                    a3.append(" -- '");
                    a3.append(build.replaceAll("'", "'\"'\"'"));
                    a3.append("'");
                    if (content != null) {
                        a3.append(" << $$$");
                    }
                    logger.config(a3.toString());
                }
            }
            b = (b3 && numRetries > 0);
            buildRequest.setTimeout(this.connectTimeout, this.readTimeout);
            try {
                final LowLevelHttpResponse v5 = buildRequest.execute();
                boolean v6 = false;
                try {
                    a1 = new HttpResponse(this, v5);
                    v6 = true;
                }
                finally {
                    if (!v6) {
                        final InputStream v7 = v5.getContent();
                        if (v7 != null) {
                            v7.close();
                        }
                    }
                }
            }
            catch (IOException v8) {
                if (!this.retryOnExecuteIOException && (this.ioExceptionHandler == null || !this.ioExceptionHandler.handleIOException(this, b))) {
                    throw v8;
                }
                ex = v8;
                if (b2) {
                    logger.log(Level.WARNING, "exception thrown while executing request", v8);
                }
            }
            boolean v9 = false;
            try {
                if (a1 != null && !a1.isSuccessStatusCode()) {
                    boolean v6 = false;
                    if (this.unsuccessfulResponseHandler != null) {
                        v6 = this.unsuccessfulResponseHandler.handleResponse(this, a1, b);
                    }
                    if (!v6) {
                        if (this.handleRedirect(a1.getStatusCode(), a1.getHeaders())) {
                            v6 = true;
                        }
                        else if (b && this.backOffPolicy != null && this.backOffPolicy.isBackOffRequired(a1.getStatusCode())) {
                            final long v10 = this.backOffPolicy.getNextBackOffMillis();
                            if (v10 != -1L) {
                                try {
                                    this.sleeper.sleep(v10);
                                }
                                catch (InterruptedException ex2) {}
                                v6 = true;
                            }
                        }
                    }
                    b &= v6;
                    if (b) {
                        a1.ignore();
                    }
                }
                else {
                    b &= (a1 == null);
                }
                --numRetries;
                v9 = true;
            }
            finally {
                if (a1 != null && !v9) {
                    a1.disconnect();
                }
            }
        } while (b);
        if (a1 == null) {
            throw ex;
        }
        if (this.responseInterceptor != null) {
            this.responseInterceptor.interceptResponse(a1);
        }
        if (this.throwExceptionOnExecuteError && !a1.isSuccessStatusCode()) {
            try {
                throw new HttpResponseException(a1);
            }
            finally {
                a1.disconnect();
            }
        }
        return a1;
    }
    
    @Beta
    public Future<HttpResponse> executeAsync(final Executor a1) {
        final FutureTask<HttpResponse> v1 = new FutureTask<HttpResponse>(new Callable<HttpResponse>() {
            final /* synthetic */ HttpRequest this$0;
            
            HttpRequest$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public HttpResponse call() throws Exception {
                return this.this$0.execute();
            }
            
            @Override
            public /* bridge */ Object call() throws Exception {
                return this.call();
            }
        });
        a1.execute(v1);
        return v1;
    }
    
    @Beta
    public Future<HttpResponse> executeAsync() {
        return this.executeAsync(Executors.newSingleThreadExecutor());
    }
    
    public boolean handleRedirect(final int a1, final HttpHeaders a2) {
        final String v1 = a2.getLocation();
        if (this.getFollowRedirects() && HttpStatusCodes.isRedirect(a1) && v1 != null) {
            this.setUrl(new GenericUrl(this.url.toURL(v1)));
            if (a1 == 303) {
                this.setRequestMethod("GET");
                this.setContent(null);
            }
            this.headers.setAuthorization((String)null);
            this.headers.setIfMatch(null);
            this.headers.setIfNoneMatch(null);
            this.headers.setIfModifiedSince(null);
            this.headers.setIfUnmodifiedSince(null);
            this.headers.setIfRange(null);
            return true;
        }
        return false;
    }
    
    public Sleeper getSleeper() {
        return this.sleeper;
    }
    
    public HttpRequest setSleeper(final Sleeper a1) {
        this.sleeper = Preconditions.checkNotNull(a1);
        return this;
    }
}
