package com.google.api.client.googleapis.batch;

import java.util.*;
import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.http.*;

final class BatchUnparsedResponse
{
    private final String boundary;
    private final List<BatchRequest.RequestInfo<?, ?>> requestInfos;
    private final InputStream inputStream;
    boolean hasNext;
    List<BatchRequest.RequestInfo<?, ?>> unsuccessfulRequestInfos;
    boolean backOffRequired;
    private int contentId;
    private final boolean retryAllowed;
    
    BatchUnparsedResponse(final InputStream a1, final String a2, final List<BatchRequest.RequestInfo<?, ?>> a3, final boolean a4) throws IOException {
        super();
        this.hasNext = true;
        this.unsuccessfulRequestInfos = new ArrayList<BatchRequest.RequestInfo<?, ?>>();
        this.contentId = 0;
        this.boundary = a2;
        this.requestInfos = a3;
        this.retryAllowed = a4;
        this.inputStream = a1;
        this.checkForFinalBoundary(this.readLine());
    }
    
    void parseNextResponse() throws IOException {
        ++this.contentId;
        String s;
        while ((s = this.readLine()) != null && !s.equals("")) {}
        final String line = this.readLine();
        final String[] split = line.split(" ");
        final int int1 = Integer.parseInt(split[1]);
        final List<String> a3 = new ArrayList<String>();
        final List<String> a4 = new ArrayList<String>();
        long long1 = -1L;
        while ((s = this.readLine()) != null && !s.equals("")) {
            final String[] v1 = s.split(": ", 2);
            final String v2 = v1[0];
            final String v3 = v1[1];
            a3.add(v2);
            a4.add(v3);
            if ("Content-Length".equalsIgnoreCase(v2.trim())) {
                long1 = Long.parseLong(v3);
            }
        }
        InputStream v5;
        if (long1 == -1L) {
            final ByteArrayOutputStream v4 = new ByteArrayOutputStream();
            while ((s = this.readRawLine()) != null && !s.startsWith(this.boundary)) {
                v4.write(s.getBytes("ISO-8859-1"));
            }
            v5 = trimCrlf(v4.toByteArray());
            s = trimCrlf(s);
        }
        else {
            v5 = new FilterInputStream(ByteStreams.limit(this.inputStream, long1)) {
                final /* synthetic */ BatchUnparsedResponse this$0;
                
                BatchUnparsedResponse$1(final InputStream a1) {
                    this.this$0 = this$0;
                    super(a1);
                }
                
                @Override
                public void close() {
                }
            };
        }
        final HttpResponse v6 = this.getFakeResponse(int1, v5, a3, a4);
        this.parseAndCallback(this.requestInfos.get(this.contentId - 1), int1, v6);
        while (true) {
            if (v5.skip(long1) <= 0L) {
                if (v5.read() != -1) {
                    continue;
                }
                break;
            }
        }
        if (long1 != -1L) {
            s = this.readLine();
        }
        while (s != null && s.length() == 0) {
            s = this.readLine();
        }
        this.checkForFinalBoundary(s);
    }
    
    private <T, E> void parseAndCallback(final BatchRequest.RequestInfo<T, E> v-7, final int v-6, final HttpResponse v-5) throws IOException {
        final BatchCallback<T, E> callback = v-7.callback;
        final HttpHeaders headers = v-5.getHeaders();
        final HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler = v-7.request.getUnsuccessfulResponseHandler();
        final BackOffPolicy backOffPolicy = v-7.request.getBackOffPolicy();
        this.backOffRequired = false;
        if (HttpStatusCodes.isSuccess(v-6)) {
            if (callback == null) {
                return;
            }
            final T a1 = this.getParsedDataClass(v-7.dataClass, v-5, v-7);
            callback.onSuccess(a1, headers);
        }
        else {
            final HttpContent a2 = v-7.request.getContent();
            final boolean v1 = this.retryAllowed && (a2 == null || a2.retrySupported());
            boolean v2 = false;
            boolean v3 = false;
            if (unsuccessfulResponseHandler != null) {
                v2 = unsuccessfulResponseHandler.handleResponse(v-7.request, v-5, v1);
            }
            if (!v2) {
                if (v-7.request.handleRedirect(v-5.getStatusCode(), v-5.getHeaders())) {
                    v3 = true;
                }
                else if (v1 && backOffPolicy != null && backOffPolicy.isBackOffRequired(v-5.getStatusCode())) {
                    this.backOffRequired = true;
                }
            }
            if (v1 && (v2 || this.backOffRequired || v3)) {
                this.unsuccessfulRequestInfos.add(v-7);
            }
            else {
                if (callback == null) {
                    return;
                }
                final E a3 = this.getParsedDataClass(v-7.errorClass, v-5, v-7);
                callback.onFailure(a3, headers);
            }
        }
    }
    
    private <A, T, E> A getParsedDataClass(final Class<A> a1, final HttpResponse a2, final BatchRequest.RequestInfo<T, E> a3) throws IOException {
        if (a1 == Void.class) {
            return null;
        }
        return a3.request.getParser().parseAndClose(a2.getContent(), a2.getContentCharset(), a1);
    }
    
    private HttpResponse getFakeResponse(final int a1, final InputStream a2, final List<String> a3, final List<String> a4) throws IOException {
        final HttpRequest v1 = new FakeResponseHttpTransport(a1, a2, a3, a4).createRequestFactory().buildPostRequest(new GenericUrl("http://google.com/"), null);
        v1.setLoggingEnabled(false);
        v1.setThrowExceptionOnExecuteError(false);
        return v1.execute();
    }
    
    private String readRawLine() throws IOException {
        int v0 = this.inputStream.read();
        if (v0 == -1) {
            return null;
        }
        final StringBuilder v2 = new StringBuilder();
        while (v0 != -1) {
            v2.append((char)v0);
            if (v0 == 10) {
                break;
            }
            v0 = this.inputStream.read();
        }
        return v2.toString();
    }
    
    private String readLine() throws IOException {
        return trimCrlf(this.readRawLine());
    }
    
    private static String trimCrlf(final String a1) {
        if (a1.endsWith("\r\n")) {
            return a1.substring(0, a1.length() - 2);
        }
        if (a1.endsWith("\n")) {
            return a1.substring(0, a1.length() - 1);
        }
        return a1;
    }
    
    private static InputStream trimCrlf(final byte[] a1) {
        int v1 = a1.length;
        if (v1 > 0 && a1[v1 - 1] == 10) {
            --v1;
        }
        if (v1 > 0 && a1[v1 - 1] == 13) {
            --v1;
        }
        return new ByteArrayInputStream(a1, 0, v1);
    }
    
    private void checkForFinalBoundary(final String a1) throws IOException {
        if (a1.equals(String.valueOf(this.boundary).concat("--"))) {
            this.hasNext = false;
            this.inputStream.close();
        }
    }
    
    private static class FakeResponseHttpTransport extends HttpTransport
    {
        private int statusCode;
        private InputStream partContent;
        private List<String> headerNames;
        private List<String> headerValues;
        
        FakeResponseHttpTransport(final int a1, final InputStream a2, final List<String> a3, final List<String> a4) {
            super();
            this.statusCode = a1;
            this.partContent = a2;
            this.headerNames = a3;
            this.headerValues = a4;
        }
        
        @Override
        protected LowLevelHttpRequest buildRequest(final String a1, final String a2) {
            return new FakeLowLevelHttpRequest(this.partContent, this.statusCode, this.headerNames, this.headerValues);
        }
    }
    
    private static class FakeLowLevelHttpRequest extends LowLevelHttpRequest
    {
        private InputStream partContent;
        private int statusCode;
        private List<String> headerNames;
        private List<String> headerValues;
        
        FakeLowLevelHttpRequest(final InputStream a1, final int a2, final List<String> a3, final List<String> a4) {
            super();
            this.partContent = a1;
            this.statusCode = a2;
            this.headerNames = a3;
            this.headerValues = a4;
        }
        
        @Override
        public void addHeader(final String a1, final String a2) {
        }
        
        @Override
        public LowLevelHttpResponse execute() {
            final FakeLowLevelHttpResponse v1 = new FakeLowLevelHttpResponse(this.partContent, this.statusCode, this.headerNames, this.headerValues);
            return v1;
        }
    }
    
    private static class FakeLowLevelHttpResponse extends LowLevelHttpResponse
    {
        private InputStream partContent;
        private int statusCode;
        private List<String> headerNames;
        private List<String> headerValues;
        
        FakeLowLevelHttpResponse(final InputStream a1, final int a2, final List<String> a3, final List<String> a4) {
            super();
            this.headerNames = new ArrayList<String>();
            this.headerValues = new ArrayList<String>();
            this.partContent = a1;
            this.statusCode = a2;
            this.headerNames = a3;
            this.headerValues = a4;
        }
        
        @Override
        public InputStream getContent() {
            return this.partContent;
        }
        
        @Override
        public int getStatusCode() {
            return this.statusCode;
        }
        
        @Override
        public String getContentEncoding() {
            return null;
        }
        
        @Override
        public long getContentLength() {
            return 0L;
        }
        
        @Override
        public String getContentType() {
            return null;
        }
        
        @Override
        public String getStatusLine() {
            return null;
        }
        
        @Override
        public String getReasonPhrase() {
            return null;
        }
        
        @Override
        public int getHeaderCount() {
            return this.headerNames.size();
        }
        
        @Override
        public String getHeaderName(final int a1) {
            return this.headerNames.get(a1);
        }
        
        @Override
        public String getHeaderValue(final int a1) {
            return this.headerValues.get(a1);
        }
    }
}
