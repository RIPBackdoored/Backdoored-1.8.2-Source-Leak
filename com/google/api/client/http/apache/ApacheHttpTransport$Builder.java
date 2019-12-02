package com.google.api.client.http.apache;

import org.apache.http.conn.ssl.*;
import org.apache.http.params.*;
import java.net.*;
import org.apache.http.*;
import org.apache.http.conn.params.*;
import java.security.*;
import java.io.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;
import org.apache.http.client.*;

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
