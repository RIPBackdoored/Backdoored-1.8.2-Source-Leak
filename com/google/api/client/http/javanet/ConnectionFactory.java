package com.google.api.client.http.javanet;

import java.net.*;
import java.io.*;

public interface ConnectionFactory
{
    HttpURLConnection openConnection(final URL p0) throws IOException, ClassCastException;
}
