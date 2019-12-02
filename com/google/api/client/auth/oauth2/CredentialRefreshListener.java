package com.google.api.client.auth.oauth2;

import java.io.*;

public interface CredentialRefreshListener
{
    void onTokenResponse(final Credential p0, final TokenResponse p1) throws IOException;
    
    void onTokenErrorResponse(final Credential p0, final TokenErrorResponse p1) throws IOException;
}
