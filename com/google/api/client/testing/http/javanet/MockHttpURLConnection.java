package com.google.api.client.testing.http.javanet;

import java.net.*;
import java.io.*;
import com.google.api.client.util.*;
import java.util.*;

@Beta
public class MockHttpURLConnection extends HttpURLConnection
{
    private boolean doOutputCalled;
    private OutputStream outputStream;
    @Deprecated
    public static final byte[] INPUT_BUF;
    @Deprecated
    public static final byte[] ERROR_BUF;
    private InputStream inputStream;
    private InputStream errorStream;
    private Map<String, List<String>> headers;
    
    public MockHttpURLConnection(final URL a1) {
        super(a1);
        this.outputStream = new ByteArrayOutputStream(0);
        this.inputStream = null;
        this.errorStream = null;
        this.headers = new LinkedHashMap<String, List<String>>();
    }
    
    @Override
    public void disconnect() {
    }
    
    @Override
    public boolean usingProxy() {
        return false;
    }
    
    @Override
    public void connect() throws IOException {
    }
    
    @Override
    public int getResponseCode() throws IOException {
        return this.responseCode;
    }
    
    @Override
    public void setDoOutput(final boolean a1) {
        this.doOutputCalled = true;
    }
    
    @Override
    public OutputStream getOutputStream() throws IOException {
        if (this.outputStream != null) {
            return this.outputStream;
        }
        return super.getOutputStream();
    }
    
    public final boolean doOutputCalled() {
        return this.doOutputCalled;
    }
    
    public MockHttpURLConnection setOutputStream(final OutputStream a1) {
        this.outputStream = a1;
        return this;
    }
    
    public MockHttpURLConnection setResponseCode(final int a1) {
        Preconditions.checkArgument(a1 >= -1);
        this.responseCode = a1;
        return this;
    }
    
    public MockHttpURLConnection addHeader(final String v1, final String v2) {
        Preconditions.checkNotNull(v1);
        Preconditions.checkNotNull(v2);
        if (this.headers.containsKey(v1)) {
            this.headers.get(v1).add(v2);
        }
        else {
            final List<String> a1 = new ArrayList<String>();
            a1.add(v2);
            this.headers.put(v1, a1);
        }
        return this;
    }
    
    public MockHttpURLConnection setInputStream(final InputStream a1) {
        Preconditions.checkNotNull(a1);
        if (this.inputStream == null) {
            this.inputStream = a1;
        }
        return this;
    }
    
    public MockHttpURLConnection setErrorStream(final InputStream a1) {
        Preconditions.checkNotNull(a1);
        if (this.errorStream == null) {
            this.errorStream = a1;
        }
        return this;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        if (this.responseCode < 400) {
            return this.inputStream;
        }
        throw new IOException();
    }
    
    @Override
    public InputStream getErrorStream() {
        return this.errorStream;
    }
    
    @Override
    public Map<String, List<String>> getHeaderFields() {
        return this.headers;
    }
    
    @Override
    public String getHeaderField(final String a1) {
        final List<String> v1 = this.headers.get(a1);
        return (v1 == null) ? null : v1.get(0);
    }
    
    static {
        INPUT_BUF = new byte[1];
        ERROR_BUF = new byte[5];
    }
}
