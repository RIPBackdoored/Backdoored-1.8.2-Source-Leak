package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;
import com.google.api.client.util.escape.*;
import java.util.*;
import java.security.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public final class OAuthParameters implements HttpExecuteInterceptor, HttpRequestInitializer
{
    private static final SecureRandom RANDOM;
    public OAuthSigner signer;
    public String callback;
    public String consumerKey;
    public String nonce;
    public String realm;
    public String signature;
    public String signatureMethod;
    public String timestamp;
    public String token;
    public String verifier;
    public String version;
    private static final PercentEscaper ESCAPER;
    
    public OAuthParameters() {
        super();
    }
    
    public void computeNonce() {
        this.nonce = Long.toHexString(Math.abs(OAuthParameters.RANDOM.nextLong()));
    }
    
    public void computeTimestamp() {
        this.timestamp = Long.toString(System.currentTimeMillis() / 1000L);
    }
    
    public void computeSignature(final String v-6, final GenericUrl v-5) throws GeneralSecurityException {
        final OAuthSigner signer = this.signer;
        final String signatureMethod = signer.getSignatureMethod();
        this.signatureMethod = signatureMethod;
        final String a4 = signatureMethod;
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        this.putParameterIfValueNotNull(treeMap, "oauth_callback", this.callback);
        this.putParameterIfValueNotNull(treeMap, "oauth_consumer_key", this.consumerKey);
        this.putParameterIfValueNotNull(treeMap, "oauth_nonce", this.nonce);
        this.putParameterIfValueNotNull(treeMap, "oauth_signature_method", a4);
        this.putParameterIfValueNotNull(treeMap, "oauth_timestamp", this.timestamp);
        this.putParameterIfValueNotNull(treeMap, "oauth_token", this.token);
        this.putParameterIfValueNotNull(treeMap, "oauth_verifier", this.verifier);
        this.putParameterIfValueNotNull(treeMap, "oauth_version", this.version);
        for (final Map.Entry<String, Object> v0 : v-5.entrySet()) {
            final Object v2 = v0.getValue();
            if (v2 != null) {
                final String a2 = v0.getKey();
                if (v2 instanceof Collection) {
                    for (final Object a3 : (Collection)v2) {
                        this.putParameter(treeMap, a2, a3);
                    }
                }
                else {
                    this.putParameter(treeMap, a2, v2);
                }
            }
        }
        final StringBuilder sb = new StringBuilder();
        boolean v3 = true;
        for (final Map.Entry<String, String> v4 : treeMap.entrySet()) {
            if (v3) {
                v3 = false;
            }
            else {
                sb.append('&');
            }
            sb.append(v4.getKey());
            final String v5 = v4.getValue();
            if (v5 != null) {
                sb.append('=').append(v5);
            }
        }
        final String v6 = sb.toString();
        final GenericUrl v7 = new GenericUrl();
        final String v5 = v-5.getScheme();
        v7.setScheme(v5);
        v7.setHost(v-5.getHost());
        v7.setPathParts(v-5.getPathParts());
        int v8 = v-5.getPort();
        if (("http".equals(v5) && v8 == 80) || ("https".equals(v5) && v8 == 443)) {
            v8 = -1;
        }
        v7.setPort(v8);
        final String v9 = v7.build();
        final StringBuilder v10 = new StringBuilder();
        v10.append(escape(v-6)).append('&');
        v10.append(escape(v9)).append('&');
        v10.append(escape(v6));
        final String v11 = v10.toString();
        this.signature = signer.computeSignature(v11);
    }
    
    public String getAuthorizationHeader() {
        final StringBuilder v1 = new StringBuilder("OAuth");
        this.appendParameter(v1, "realm", this.realm);
        this.appendParameter(v1, "oauth_callback", this.callback);
        this.appendParameter(v1, "oauth_consumer_key", this.consumerKey);
        this.appendParameter(v1, "oauth_nonce", this.nonce);
        this.appendParameter(v1, "oauth_signature", this.signature);
        this.appendParameter(v1, "oauth_signature_method", this.signatureMethod);
        this.appendParameter(v1, "oauth_timestamp", this.timestamp);
        this.appendParameter(v1, "oauth_token", this.token);
        this.appendParameter(v1, "oauth_verifier", this.verifier);
        this.appendParameter(v1, "oauth_version", this.version);
        return v1.substring(0, v1.length() - 1);
    }
    
    private void appendParameter(final StringBuilder a1, final String a2, final String a3) {
        if (a3 != null) {
            a1.append(' ').append(escape(a2)).append("=\"").append(escape(a3)).append("\",");
        }
    }
    
    private void putParameterIfValueNotNull(final TreeMap<String, String> a1, final String a2, final String a3) {
        if (a3 != null) {
            this.putParameter(a1, a2, a3);
        }
    }
    
    private void putParameter(final TreeMap<String, String> a1, final String a2, final Object a3) {
        a1.put(escape(a2), (a3 == null) ? null : escape(a3.toString()));
    }
    
    public static String escape(final String a1) {
        return OAuthParameters.ESCAPER.escape(a1);
    }
    
    @Override
    public void initialize(final HttpRequest a1) throws IOException {
        a1.setInterceptor(this);
    }
    
    @Override
    public void intercept(final HttpRequest v0) throws IOException {
        this.computeNonce();
        this.computeTimestamp();
        try {
            this.computeSignature(v0.getRequestMethod(), v0.getUrl());
        }
        catch (GeneralSecurityException v) {
            final IOException a1 = new IOException();
            a1.initCause(v);
            throw a1;
        }
        v0.getHeaders().setAuthorization(this.getAuthorizationHeader());
    }
    
    static {
        RANDOM = new SecureRandom();
        ESCAPER = new PercentEscaper("-_.~", false);
    }
}
