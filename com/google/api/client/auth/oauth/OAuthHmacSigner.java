package com.google.api.client.auth.oauth;

import javax.crypto.spec.*;
import com.google.api.client.util.*;
import javax.crypto.*;
import java.security.*;

@Beta
public final class OAuthHmacSigner implements OAuthSigner
{
    public String clientSharedSecret;
    public String tokenSharedSecret;
    
    public OAuthHmacSigner() {
        super();
    }
    
    @Override
    public String getSignatureMethod() {
        return "HMAC-SHA1";
    }
    
    @Override
    public String computeSignature(final String a1) throws GeneralSecurityException {
        final StringBuilder v1 = new StringBuilder();
        final String v2 = this.clientSharedSecret;
        if (v2 != null) {
            v1.append(OAuthParameters.escape(v2));
        }
        v1.append('&');
        final String v3 = this.tokenSharedSecret;
        if (v3 != null) {
            v1.append(OAuthParameters.escape(v3));
        }
        final String v4 = v1.toString();
        final SecretKey v5 = new SecretKeySpec(StringUtils.getBytesUtf8(v4), "HmacSHA1");
        final Mac v6 = Mac.getInstance("HmacSHA1");
        v6.init(v5);
        return Base64.encodeBase64String(v6.doFinal(StringUtils.getBytesUtf8(a1)));
    }
}
