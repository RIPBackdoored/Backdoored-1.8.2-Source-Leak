package com.google.api.client.util;

import java.io.*;

public class ByteArrayStreamingContent implements StreamingContent
{
    private final byte[] byteArray;
    private final int offset;
    private final int length;
    
    public ByteArrayStreamingContent(final byte[] a1) {
        this(a1, 0, a1.length);
    }
    
    public ByteArrayStreamingContent(final byte[] a1, final int a2, final int a3) {
        super();
        this.byteArray = Preconditions.checkNotNull(a1);
        Preconditions.checkArgument(a2 >= 0 && a3 >= 0 && a2 + a3 <= a1.length);
        this.offset = a2;
        this.length = a3;
    }
    
    @Override
    public void writeTo(final OutputStream a1) throws IOException {
        a1.write(this.byteArray, this.offset, this.length);
        a1.flush();
    }
}
