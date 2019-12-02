package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public final class ByteArrayContent extends AbstractInputStreamContent
{
    private final byte[] byteArray;
    private final int offset;
    private final int length;
    
    public ByteArrayContent(final String a1, final byte[] a2) {
        this(a1, a2, 0, a2.length);
    }
    
    public ByteArrayContent(final String a1, final byte[] a2, final int a3, final int a4) {
        super(a1);
        this.byteArray = Preconditions.checkNotNull(a2);
        Preconditions.checkArgument(a3 >= 0 && a4 >= 0 && a3 + a4 <= a2.length, "offset %s, length %s, array length %s", a3, a4, a2.length);
        this.offset = a3;
        this.length = a4;
    }
    
    public static ByteArrayContent fromString(final String a1, final String a2) {
        return new ByteArrayContent(a1, StringUtils.getBytesUtf8(a2));
    }
    
    @Override
    public long getLength() {
        return this.length;
    }
    
    @Override
    public boolean retrySupported() {
        return true;
    }
    
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.byteArray, this.offset, this.length);
    }
    
    @Override
    public ByteArrayContent setType(final String a1) {
        return (ByteArrayContent)super.setType(a1);
    }
    
    @Override
    public ByteArrayContent setCloseInputStream(final boolean a1) {
        return (ByteArrayContent)super.setCloseInputStream(a1);
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setCloseInputStream(final boolean closeInputStream) {
        return this.setCloseInputStream(closeInputStream);
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setType(final String type) {
        return this.setType(type);
    }
}
