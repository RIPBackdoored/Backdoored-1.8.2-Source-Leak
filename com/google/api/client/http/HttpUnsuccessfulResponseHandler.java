package com.google.api.client.http;

import java.io.*;

public interface HttpUnsuccessfulResponseHandler
{
    boolean handleResponse(final HttpRequest p0, final HttpResponse p1, final boolean p2) throws IOException;
}
