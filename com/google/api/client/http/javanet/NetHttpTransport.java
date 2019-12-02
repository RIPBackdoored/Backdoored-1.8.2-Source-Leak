package com.google.api.client.http.javanet;

import java.util.*;
import java.net.*;
import com.google.api.client.http.*;
import java.io.*;
import java.security.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;

public final class NetHttpTransport extends HttpTransport
{
    private static final String[] SUPPORTED_METHODS;
    private static final String SHOULD_USE_PROXY_FLAG = "com.google.api.client.should_use_proxy";
    private final ConnectionFactory connectionFactory;
    private final SSLSocketFactory sslSocketFactory;
    private final HostnameVerifier hostnameVerifier;
    
    private static Proxy defaultProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("https.proxyHost"), Integer.parseInt(System.getProperty("https.proxyPort"))));
    }
    
    public NetHttpTransport() {
        this((ConnectionFactory)null, null, null);
    }
    
    NetHttpTransport(final Proxy a1, final SSLSocketFactory a2, final HostnameVerifier a3) {
        this(new DefaultConnectionFactory(a1), a2, a3);
    }
    
    NetHttpTransport(final ConnectionFactory a1, final SSLSocketFactory a2, final HostnameVerifier a3) {
        super();
        this.connectionFactory = this.getConnectionFactory(a1);
        this.sslSocketFactory = a2;
        this.hostnameVerifier = a3;
    }
    
    private ConnectionFactory getConnectionFactory(final ConnectionFactory a1) {
        if (a1 != null) {
            return a1;
        }
        if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            return new DefaultConnectionFactory(defaultProxy());
        }
        return new DefaultConnectionFactory();
    }
    
    @Override
    public boolean supportsMethod(final String a1) {
        return Arrays.binarySearch(NetHttpTransport.SUPPORTED_METHODS, a1) >= 0;
    }
    
    @Override
    protected NetHttpRequest buildRequest(final String v1, final String v2) throws IOException {
        Preconditions.checkArgument(this.supportsMethod(v1), "HTTP method %s not supported", v1);
        final URL v3 = new URL(v2);
        final HttpURLConnection v4 = this.connectionFactory.openConnection(v3);
        v4.setRequestMethod(v1);
        if (v4 instanceof HttpsURLConnection) {
            final HttpsURLConnection a1 = (HttpsURLConnection)v4;
            if (this.hostnameVerifier != null) {
                a1.setHostnameVerifier(this.hostnameVerifier);
            }
            if (this.sslSocketFactory != null) {
                a1.setSSLSocketFactory(this.sslSocketFactory);
            }
        }
        return new NetHttpRequest(v4);
    }
    
    @Override
    protected /* bridge */ LowLevelHttpRequest buildRequest(final String v1, final String v2) throws IOException {
        return this.buildRequest(v1, v2);
    }
    
    static /* bridge */ Proxy access$000() {
        return defaultProxy();
    }
    
    static {
        Arrays.sort(SUPPORTED_METHODS = new String[] { "DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE" });
    }
    
    public static final class Builder
    {
        private SSLSocketFactory sslSocketFactory;
        private HostnameVerifier hostnameVerifier;
        private Proxy proxy;
        private ConnectionFactory connectionFactory;
        
        public Builder() {
            super();
        }
        
        public Builder setProxy(final Proxy a1) {
            this.proxy = a1;
            return this;
        }
        
        public Builder setConnectionFactory(final ConnectionFactory a1) {
            this.connectionFactory = a1;
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
            return this.setSslSocketFactory(v1.getSocketFactory());
        }
        
        @Beta
        public Builder doNotValidateCertificate() throws GeneralSecurityException {
            this.hostnameVerifier = SslUtils.trustAllHostnameVerifier();
            this.sslSocketFactory = SslUtils.trustAllSSLContext().getSocketFactory();
            return this;
        }
        
        public SSLSocketFactory getSslSocketFactory() {
            return this.sslSocketFactory;
        }
        
        public Builder setSslSocketFactory(final SSLSocketFactory a1) {
            this.sslSocketFactory = a1;
            return this;
        }
        
        public HostnameVerifier getHostnameVerifier() {
            return this.hostnameVerifier;
        }
        
        public Builder setHostnameVerifier(final HostnameVerifier a1) {
            this.hostnameVerifier = a1;
            return this;
        }
        
        public NetHttpTransport build() {
            if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
                this.setProxy(defaultProxy());
            }
            return (this.proxy == null) ? new NetHttpTransport(this.connectionFactory, this.sslSocketFactory, this.hostnameVerifier) : new NetHttpTransport(this.proxy, this.sslSocketFactory, this.hostnameVerifier);
        }
    }
}
