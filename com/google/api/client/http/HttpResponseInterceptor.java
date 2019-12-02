package com.google.api.client.http;

import java.io.*;

public interface HttpResponseInterceptor
{
    void interceptResponse(final HttpResponse p0) throws IOException;
}
