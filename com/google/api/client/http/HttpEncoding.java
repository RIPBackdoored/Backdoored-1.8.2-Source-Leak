package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public interface HttpEncoding
{
    String getName();
    
    void encode(final StreamingContent p0, final OutputStream p1) throws IOException;
}
