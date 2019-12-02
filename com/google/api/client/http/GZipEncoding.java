package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;
import java.util.zip.*;

public class GZipEncoding implements HttpEncoding
{
    public GZipEncoding() {
        super();
    }
    
    @Override
    public String getName() {
        return "gzip";
    }
    
    @Override
    public void encode(final StreamingContent a1, final OutputStream a2) throws IOException {
        final OutputStream v1 = new BufferedOutputStream(a2) {
            final /* synthetic */ GZipEncoding this$0;
            
            GZipEncoding$1(final OutputStream a2) {
                this.this$0 = a1;
                super(a2);
            }
            
            @Override
            public void close() throws IOException {
                try {
                    this.flush();
                }
                catch (IOException ex) {}
            }
        };
        final GZIPOutputStream v2 = new GZIPOutputStream(v1);
        a1.writeTo(v2);
        v2.close();
    }
}
