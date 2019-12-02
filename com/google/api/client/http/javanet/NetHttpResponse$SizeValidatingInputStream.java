package com.google.api.client.http.javanet;

import java.io.*;

private final class SizeValidatingInputStream extends FilterInputStream
{
    private long bytesRead;
    final /* synthetic */ NetHttpResponse this$0;
    
    public SizeValidatingInputStream(final NetHttpResponse this$0, final InputStream a1) {
        this.this$0 = this$0;
        super(a1);
        this.bytesRead = 0L;
    }
    
    @Override
    public int read(final byte[] a1, final int a2, final int a3) throws IOException {
        final int v1 = this.in.read(a1, a2, a3);
        if (v1 == -1) {
            this.throwIfFalseEOF();
        }
        else {
            this.bytesRead += v1;
        }
        return v1;
    }
    
    @Override
    public int read() throws IOException {
        final int v1 = this.in.read();
        if (v1 == -1) {
            this.throwIfFalseEOF();
        }
        else {
            ++this.bytesRead;
        }
        return v1;
    }
    
    private void throwIfFalseEOF() throws IOException {
        final long v1 = this.this$0.getContentLength();
        if (v1 == -1L) {
            return;
        }
        if (this.bytesRead != 0L && this.bytesRead < v1) {
            throw new IOException("Connection closed prematurely: bytesRead = " + this.bytesRead + ", Content-Length = " + v1);
        }
    }
}
