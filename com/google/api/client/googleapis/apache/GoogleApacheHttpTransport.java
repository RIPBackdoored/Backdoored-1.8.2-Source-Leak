package com.google.api.client.googleapis.apache;

import com.google.api.client.http.apache.*;
import com.google.api.client.googleapis.*;
import java.security.*;
import java.io.*;

public final class GoogleApacheHttpTransport
{
    public static ApacheHttpTransport newTrustedTransport() throws GeneralSecurityException, IOException {
        return new ApacheHttpTransport.Builder().trustCertificates(GoogleUtils.getCertificateTrustStore()).build();
    }
    
    private GoogleApacheHttpTransport() {
        super();
    }
}
