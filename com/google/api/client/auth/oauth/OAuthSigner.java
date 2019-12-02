package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;
import java.security.*;

@Beta
public interface OAuthSigner
{
    String getSignatureMethod();
    
    String computeSignature(final String p0) throws GeneralSecurityException;
}
