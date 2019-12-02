package com.google.api.client.http.javanet;

import com.google.api.client.http.*;
import java.net.*;
import java.util.*;
import java.io.*;

final class NetHttpResponse extends LowLevelHttpResponse
{
    private final HttpURLConnection connection;
    private final int responseCode;
    private final String responseMessage;
    private final ArrayList<String> headerNames;
    private final ArrayList<String> headerValues;
    
    NetHttpResponse(final HttpURLConnection v-5) throws IOException {
        super();
        this.headerNames = new ArrayList<String>();
        this.headerValues = new ArrayList<String>();
        this.connection = v-5;
        final int responseCode = v-5.getResponseCode();
        this.responseCode = ((responseCode == -1) ? 0 : responseCode);
        this.responseMessage = v-5.getResponseMessage();
        final List<String> headerNames = this.headerNames;
        final List<String> headerValues = this.headerValues;
        for (final Map.Entry<String, List<String>> v0 : v-5.getHeaderFields().entrySet()) {
            final String v2 = v0.getKey();
            if (v2 != null) {
                for (final String a1 : v0.getValue()) {
                    if (a1 != null) {
                        headerNames.add(v2);
                        headerValues.add(a1);
                    }
                }
            }
        }
    }
    
    @Override
    public int getStatusCode() {
        return this.responseCode;
    }
    
    @Override
    public InputStream getContent() throws IOException {
        InputStream v0 = null;
        try {
            v0 = this.connection.getInputStream();
        }
        catch (IOException v2) {
            v0 = this.connection.getErrorStream();
        }
        return (v0 == null) ? null : new SizeValidatingInputStream(v0);
    }
    
    @Override
    public String getContentEncoding() {
        return this.connection.getContentEncoding();
    }
    
    @Override
    public long getContentLength() {
        final String v1 = this.connection.getHeaderField("Content-Length");
        return (v1 == null) ? -1L : Long.parseLong(v1);
    }
    
    @Override
    public String getContentType() {
        return this.connection.getHeaderField("Content-Type");
    }
    
    @Override
    public String getReasonPhrase() {
        return this.responseMessage;
    }
    
    @Override
    public String getStatusLine() {
        final String v1 = this.connection.getHeaderField(0);
        return (v1 != null && v1.startsWith("HTTP/1.")) ? v1 : null;
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
    
    @Override
    public void disconnect() {
        this.connection.disconnect();
    }
    
    private final class SizeValidatingInputStream extends FilterInputStream
    {
        private long bytesRead;
        final /* synthetic */ NetHttpResponse this$0;
        
        public SizeValidatingInputStream(final NetHttpResponse this$0, final InputStream a1) {
            this.this$0 = this$0;
            super(a1);
            this.bytesRead = 0L;
        }
        
        @Override
        public int read(final byte[] a1, final int a2, final int a3) throws IOException {
            final int v1 = this.in.read(a1, a2, a3);
            if (v1 == -1) {
                this.throwIfFalseEOF();
            }
            else {
                this.bytesRead += v1;
            }
            return v1;
        }
        
        @Override
        public int read() throws IOException {
            final int v1 = this.in.read();
            if (v1 == -1) {
                this.throwIfFalseEOF();
            }
            else {
                ++this.bytesRead;
            }
            return v1;
        }
        
        private void throwIfFalseEOF() throws IOException {
            final long v1 = this.this$0.getContentLength();
            if (v1 == -1L) {
                return;
            }
            if (this.bytesRead != 0L && this.bytesRead < v1) {
                throw new IOException("Connection closed prematurely: bytesRead = " + this.bytesRead + ", Content-Length = " + v1);
            }
        }
    }
}
