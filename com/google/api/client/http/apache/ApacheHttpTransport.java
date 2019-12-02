package com.google.api.client.http.apache;

import org.apache.http.conn.ssl.*;
import java.net.*;
import org.apache.http.params.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.impl.conn.tsccm.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.*;
import org.apache.http.impl.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.*;
import org.apache.http.client.methods.*;
import com.google.api.client.http.*;
import org.apache.http.*;
import org.apache.http.conn.params.*;
import java.io.*;
import java.security.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;

public final class ApacheHttpTransport extends HttpTransport
{
    private final HttpClient httpClient;
    
    public ApacheHttpTransport() {
        this((HttpClient)newDefaultHttpClient());
    }
    
    public ApacheHttpTransport(final HttpClient a1) {
        super();
        this.httpClient = a1;
        HttpParams v1 = a1.getParams();
        if (v1 == null) {
            v1 = newDefaultHttpClient().getParams();
        }
        HttpProtocolParams.setVersion(v1, (ProtocolVersion)HttpVersion.HTTP_1_1);
        v1.setBooleanParameter("http.protocol.handle-redirects", false);
    }
    
    public static DefaultHttpClient newDefaultHttpClient() {
        return newDefaultHttpClient(SSLSocketFactory.getSocketFactory(), newDefaultHttpParams(), ProxySelector.getDefault());
    }
    
    static HttpParams newDefaultHttpParams() {
        final HttpParams v1 = (HttpParams)new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(v1, false);
        HttpConnectionParams.setSocketBufferSize(v1, 8192);
        ConnManagerParams.setMaxTotalConnections(v1, 200);
        ConnManagerParams.setMaxConnectionsPerRoute(v1, (ConnPerRoute)new ConnPerRouteBean(20));
        return v1;
    }
    
    static DefaultHttpClient newDefaultHttpClient(final SSLSocketFactory a1, final HttpParams a2, final ProxySelector a3) {
        final SchemeRegistry v1 = new SchemeRegistry();
        v1.register(new Scheme("http", (SocketFactory)PlainSocketFactory.getSocketFactory(), 80));
        v1.register(new Scheme("https", (SocketFactory)a1, 443));
        final ClientConnectionManager v2 = (ClientConnectionManager)new ThreadSafeClientConnManager(a2, v1);
        final DefaultHttpClient v3 = new DefaultHttpClient(v2, a2);
        v3.setHttpRequestRetryHandler((HttpRequestRetryHandler)new DefaultHttpRequestRetryHandler(0, false));
        if (a3 != null) {
            v3.setRoutePlanner((HttpRoutePlanner)new ProxySelectorRoutePlanner(v1, a3));
        }
        return v3;
    }
    
    @Override
    public boolean supportsMethod(final String a1) {
        return true;
    }
    
    @Override
    protected ApacheHttpRequest buildRequest(final String v-1, final String v0) {
        HttpRequestBase v = null;
        if (v-1.equals("DELETE")) {
            final HttpRequestBase a1 = (HttpRequestBase)new HttpDelete(v0);
        }
        else if (v-1.equals("GET")) {
            final HttpRequestBase a2 = (HttpRequestBase)new HttpGet(v0);
        }
        else if (v-1.equals("HEAD")) {
            v = (HttpRequestBase)new HttpHead(v0);
        }
        else if (v-1.equals("POST")) {
            v = (HttpRequestBase)new HttpPost(v0);
        }
        else if (v-1.equals("PUT")) {
            v = (HttpRequestBase)new HttpPut(v0);
        }
        else if (v-1.equals("TRACE")) {
            v = (HttpRequestBase)new HttpTrace(v0);
        }
        else if (v-1.equals("OPTIONS")) {
            v = (HttpRequestBase)new HttpOptions(v0);
        }
        else {
            v = (HttpRequestBase)new HttpExtensionMethod(v-1, v0);
        }
        return new ApacheHttpRequest(this.httpClient, v);
    }
    
    @Override
    public void shutdown() {
        this.httpClient.getConnectionManager().shutdown();
    }
    
    public HttpClient getHttpClient() {
        return this.httpClient;
    }
    
    @Override
    protected /* bridge */ LowLevelHttpRequest buildRequest(final String v-1, final String v0) throws IOException {
        return this.buildRequest(v-1, v0);
    }
    
    public static final class Builder
    {
        private SSLSocketFactory socketFactory;
        private HttpParams params;
        private ProxySelector proxySelector;
        
        public Builder() {
            super();
            this.socketFactory = SSLSocketFactory.getSocketFactory();
            this.params = ApacheHttpTransport.newDefaultHttpParams();
            this.proxySelector = ProxySelector.getDefault();
        }
        
        public Builder setProxy(final HttpHost a1) {
            ConnRouteParams.setDefaultProxy(this.params, a1);
            if (a1 != null) {
                this.proxySelector = null;
            }
            return this;
        }
        
        public Builder setProxySelector(final ProxySelector a1) {
            this.proxySelector = a1;
            if (a1 != null) {
                ConnRouteParams.setDefaultProxy(this.params, (HttpHost)null);
            }
            return this;
        }
        
        public Builder trustCertificatesFromJavaKeyStore(final InputStream a1, final String a2) throws GeneralSecurityException, IOException {
            final KeyStore v1 = SecurityUtils.getJavaKeyStore();
            SecurityUtils.loadKeyStore(v1, a1, a2);
            return this.trustCertificates(v1);
        }
        
        public Builder trustCertificatesFromStream(final InputStream a1) throws GeneralSecurityException, IOException {
            final KeyStore v1 = SecurityUtils.getJavaKeyStore();
            v1.load(null, null);
            SecurityUtils.loadKeyStoreFromCertificates(v1, SecurityUtils.getX509CertificateFactory(), a1);
            return this.trustCertificates(v1);
        }
        
        public Builder trustCertificates(final KeyStore a1) throws GeneralSecurityException {
            final SSLContext v1 = SslUtils.getTlsSslContext();
            SslUtils.initSslContext(v1, a1, SslUtils.getPkixTrustManagerFactory());
            return this.setSocketFactory(new SSLSocketFactoryExtension(v1));
        }
        
        @Beta
        public Builder doNotValidateCertificate() throws GeneralSecurityException {
            (this.socketFactory = new SSLSocketFactoryExtension(SslUtils.trustAllSSLContext())).setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return this;
        }
        
        public Builder setSocketFactory(final SSLSocketFactory a1) {
            this.socketFactory = Preconditions.checkNotNull(a1);
            return this;
        }
        
        public SSLSocketFactory getSSLSocketFactory() {
            return this.socketFactory;
        }
        
        public HttpParams getHttpParams() {
            return this.params;
        }
        
        public ApacheHttpTransport build() {
            return new ApacheHttpTransport((HttpClient)ApacheHttpTransport.newDefaultHttpClient(this.socketFactory, this.params, this.proxySelector));
        }
    }
}
