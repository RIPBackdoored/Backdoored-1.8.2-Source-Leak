package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;
import java.security.*;

@Beta
public final class OAuthRsaSigner implements OAuthSigner
{
    public PrivateKey privateKey;
    
    public OAuthRsaSigner() {
        super();
    }
    
    @Override
    public String getSignatureMethod() {
        return "RSA-SHA1";
    }
    
    @Override
    public String computeSignature(final String a1) throws GeneralSecurityException {
        final Signature v1 = SecurityUtils.getSha1WithRsaSignatureAlgorithm();
        final byte[] v2 = StringUtils.getBytesUtf8(a1);
        return Base64.encodeBase64String(SecurityUtils.sign(v1, this.privateKey, v2));
    }
}
