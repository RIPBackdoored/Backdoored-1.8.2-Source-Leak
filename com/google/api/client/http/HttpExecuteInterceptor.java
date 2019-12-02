package com.google.api.client.http;

import java.io.*;

public interface HttpExecuteInterceptor
{
    void intercept(final HttpRequest p0) throws IOException;
}
