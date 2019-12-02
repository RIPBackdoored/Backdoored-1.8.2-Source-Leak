package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;

public interface AccessMethod
{
    void intercept(final HttpRequest p0, final String p1) throws IOException;
    
    String getAccessTokenFromRequest(final HttpRequest p0);
}
