package com.google.api.client.googleapis.testing.json;

import com.google.api.client.util.*;
import com.google.api.client.json.*;
import com.google.api.client.googleapis.json.*;
import com.google.api.client.testing.http.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public final class GoogleJsonResponseExceptionFactoryTesting
{
    public GoogleJsonResponseExceptionFactoryTesting() {
        super();
    }
    
    public static GoogleJsonResponseException newMock(final JsonFactory a1, final int a2, final String a3) throws IOException {
        final MockLowLevelHttpResponse v1 = new MockLowLevelHttpResponse().setStatusCode(a2).setReasonPhrase(a3);
        final MockHttpTransport v2 = new MockHttpTransport.Builder().setLowLevelHttpResponse(v1).build();
        final HttpRequest v3 = v2.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
        v3.setThrowExceptionOnExecuteError(false);
        final HttpResponse v4 = v3.execute();
        return GoogleJsonResponseException.from(a1, v4);
    }
}
