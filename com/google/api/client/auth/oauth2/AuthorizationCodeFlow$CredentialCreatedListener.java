package com.google.api.client.auth.oauth2;

import java.io.*;

public interface CredentialCreatedListener
{
    void onCredentialCreated(final Credential p0, final TokenResponse p1) throws IOException;
}
