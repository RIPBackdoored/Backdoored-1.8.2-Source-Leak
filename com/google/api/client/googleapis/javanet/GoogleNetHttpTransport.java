package com.google.api.client.googleapis.javanet;

import com.google.api.client.http.javanet.*;
import com.google.api.client.googleapis.*;
import java.security.*;
import java.io.*;

public class GoogleNetHttpTransport
{
    public static NetHttpTransport newTrustedTransport() throws GeneralSecurityException, IOException {
        return new NetHttpTransport.Builder().trustCertificates(GoogleUtils.getCertificateTrustStore()).build();
    }
    
    private GoogleNetHttpTransport() {
        super();
    }
}
