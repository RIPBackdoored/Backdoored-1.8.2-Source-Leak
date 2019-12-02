package com.google.api.client.util;

import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

public final class SslUtils
{
    public static SSLContext getSslContext() throws NoSuchAlgorithmException {
        return SSLContext.getInstance("SSL");
    }
    
    public static SSLContext getTlsSslContext() throws NoSuchAlgorithmException {
        return SSLContext.getInstance("TLS");
    }
    
    public static TrustManagerFactory getDefaultTrustManagerFactory() throws NoSuchAlgorithmException {
        return TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    }
    
    public static TrustManagerFactory getPkixTrustManagerFactory() throws NoSuchAlgorithmException {
        return TrustManagerFactory.getInstance("PKIX");
    }
    
    public static KeyManagerFactory getDefaultKeyManagerFactory() throws NoSuchAlgorithmException {
        return KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    }
    
    public static KeyManagerFactory getPkixKeyManagerFactory() throws NoSuchAlgorithmException {
        return KeyManagerFactory.getInstance("PKIX");
    }
    
    public static SSLContext initSslContext(final SSLContext a1, final KeyStore a2, final TrustManagerFactory a3) throws GeneralSecurityException {
        a3.init(a2);
        a1.init(null, a3.getTrustManagers(), null);
        return a1;
    }
    
    @Beta
    public static SSLContext trustAllSSLContext() throws GeneralSecurityException {
        final TrustManager[] v1 = { new X509TrustManager() {
                SslUtils$1() {
                    super();
                }
                
                @Override
                public void checkClientTrusted(final X509Certificate[] a1, final String a2) throws CertificateException {
                }
                
                @Override
                public void checkServerTrusted(final X509Certificate[] a1, final String a2) throws CertificateException {
                }
                
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };
        final SSLContext v2 = getTlsSslContext();
        v2.init(null, v1, null);
        return v2;
    }
    
    @Beta
    public static HostnameVerifier trustAllHostnameVerifier() {
        return new HostnameVerifier() {
            SslUtils$2() {
                super();
            }
            
            @Override
            public boolean verify(final String a1, final SSLSession a2) {
                return true;
            }
        };
    }
    
    private SslUtils() {
        super();
    }
}
