package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public interface HttpIOExceptionHandler
{
    boolean handleIOException(final HttpRequest p0, final boolean p1) throws IOException;
}
