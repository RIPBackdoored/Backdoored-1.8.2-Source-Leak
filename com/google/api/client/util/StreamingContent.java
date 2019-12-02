package com.google.api.client.util;

import java.io.*;

public interface StreamingContent
{
    void writeTo(final OutputStream p0) throws IOException;
}
