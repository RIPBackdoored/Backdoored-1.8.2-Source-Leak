package com.google.api.client.http;

import java.io.*;

class GZipEncoding$1 extends BufferedOutputStream {
    final /* synthetic */ GZipEncoding this$0;
    
    GZipEncoding$1(final GZipEncoding a1, final OutputStream a2) {
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
}