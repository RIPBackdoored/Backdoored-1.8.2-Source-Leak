package com.google.api.client.repackaged.org.apache.commons.codec.binary;

import java.io.*;

public class StringUtils
{
    public StringUtils() {
        super();
    }
    
    public static byte[] getBytesIso8859_1(final String a1) {
        return getBytesUnchecked(a1, "ISO-8859-1");
    }
    
    public static byte[] getBytesUsAscii(final String a1) {
        return getBytesUnchecked(a1, "US-ASCII");
    }
    
    public static byte[] getBytesUtf16(final String a1) {
        return getBytesUnchecked(a1, "UTF-16");
    }
    
    public static byte[] getBytesUtf16Be(final String a1) {
        return getBytesUnchecked(a1, "UTF-16BE");
    }
    
    public static byte[] getBytesUtf16Le(final String a1) {
        return getBytesUnchecked(a1, "UTF-16LE");
    }
    
    public static byte[] getBytesUtf8(final String a1) {
        return getBytesUnchecked(a1, "UTF-8");
    }
    
    public static byte[] getBytesUnchecked(final String a2, final String v1) {
        if (a2 == null) {
            return null;
        }
        try {
            return a2.getBytes(v1);
        }
        catch (UnsupportedEncodingException a3) {
            throw newIllegalStateException(v1, a3);
        }
    }
    
    private static IllegalStateException newIllegalStateException(final String a1, final UnsupportedEncodingException a2) {
        return new IllegalStateException(a1 + ": " + a2);
    }
    
    public static String newString(final byte[] a2, final String v1) {
        if (a2 == null) {
            return null;
        }
        try {
            return new String(a2, v1);
        }
        catch (UnsupportedEncodingException a3) {
            throw newIllegalStateException(v1, a3);
        }
    }
    
    public static String newStringIso8859_1(final byte[] a1) {
        return newString(a1, "ISO-8859-1");
    }
    
    public static String newStringUsAscii(final byte[] a1) {
        return newString(a1, "US-ASCII");
    }
    
    public static String newStringUtf16(final byte[] a1) {
        return newString(a1, "UTF-16");
    }
    
    public static String newStringUtf16Be(final byte[] a1) {
        return newString(a1, "UTF-16BE");
    }
    
    public static String newStringUtf16Le(final byte[] a1) {
        return newString(a1, "UTF-16LE");
    }
    
    public static String newStringUtf8(final byte[] a1) {
        return newString(a1, "UTF-8");
    }
}
