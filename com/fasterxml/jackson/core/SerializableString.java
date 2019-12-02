package com.fasterxml.jackson.core;

import java.io.*;
import java.nio.*;

public interface SerializableString
{
    String getValue();
    
    int charLength();
    
    char[] asQuotedChars();
    
    byte[] asUnquotedUTF8();
    
    byte[] asQuotedUTF8();
    
    int appendQuotedUTF8(final byte[] p0, final int p1);
    
    int appendQuoted(final char[] p0, final int p1);
    
    int appendUnquotedUTF8(final byte[] p0, final int p1);
    
    int appendUnquoted(final char[] p0, final int p1);
    
    int writeQuotedUTF8(final OutputStream p0) throws IOException;
    
    int writeUnquotedUTF8(final OutputStream p0) throws IOException;
    
    int putQuotedUTF8(final ByteBuffer p0) throws IOException;
    
    int putUnquotedUTF8(final ByteBuffer p0) throws IOException;
}
