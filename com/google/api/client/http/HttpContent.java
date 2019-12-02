package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public interface HttpContent extends StreamingContent
{
    long getLength() throws IOException;
    
    String getType();
    
    boolean retrySupported();
    
    void writeTo(final OutputStream p0) throws IOException;
}
