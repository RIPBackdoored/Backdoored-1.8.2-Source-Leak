package com.google.api.client.auth.oauth2;

import java.io.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

static final class FormEncodedBodyAccessMethod implements Credential.AccessMethod
{
    FormEncodedBodyAccessMethod() {
        super();
    }
    
    @Override
    public void intercept(final HttpRequest a1, final String a2) throws IOException {
        Preconditions.checkArgument(!"GET".equals(a1.getRequestMethod()), (Object)"HTTP GET method is not supported");
        getData(a1).put("access_token", a2);
    }
    
    @Override
    public String getAccessTokenFromRequest(final HttpRequest a1) {
        final Object v1 = getData(a1).get("access_token");
        return (v1 == null) ? null : v1.toString();
    }
    
    private static Map<String, Object> getData(final HttpRequest a1) {
        return Data.mapOf(UrlEncodedContent.getContent(a1).getData());
    }
}
