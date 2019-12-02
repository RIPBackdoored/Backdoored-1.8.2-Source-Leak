package com.fasterxml.jackson.core.util;

import java.lang.ref.*;
import com.fasterxml.jackson.core.io.*;

public class BufferRecyclers
{
    public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
    private static final ThreadLocalBufferManager _bufferRecyclerTracker;
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _encoderRef;
    
    public BufferRecyclers() {
        super();
    }
    
    public static BufferRecycler getBufferRecycler() {
        SoftReference<BufferRecycler> v1 = BufferRecyclers._recyclerRef.get();
        BufferRecycler v2 = (v1 == null) ? null : v1.get();
        if (v2 == null) {
            v2 = new BufferRecycler();
            if (BufferRecyclers._bufferRecyclerTracker != null) {
                v1 = BufferRecyclers._bufferRecyclerTracker.wrapAndTrack(v2);
            }
            else {
                v1 = new SoftReference<BufferRecycler>(v2);
            }
            BufferRecyclers._recyclerRef.set(v1);
        }
        return v2;
    }
    
    public static int releaseBuffers() {
        if (BufferRecyclers._bufferRecyclerTracker != null) {
            return BufferRecyclers._bufferRecyclerTracker.releaseBuffers();
        }
        return -1;
    }
    
    public static JsonStringEncoder getJsonStringEncoder() {
        final SoftReference<JsonStringEncoder> v1 = BufferRecyclers._encoderRef.get();
        JsonStringEncoder v2 = (v1 == null) ? null : v1.get();
        if (v2 == null) {
            v2 = new JsonStringEncoder();
            BufferRecyclers._encoderRef.set(new SoftReference<JsonStringEncoder>(v2));
        }
        return v2;
    }
    
    public static byte[] encodeAsUTF8(final String a1) {
        return getJsonStringEncoder().encodeAsUTF8(a1);
    }
    
    public static char[] quoteAsJsonText(final String a1) {
        return getJsonStringEncoder().quoteAsString(a1);
    }
    
    public static void quoteAsJsonText(final CharSequence a1, final StringBuilder a2) {
        getJsonStringEncoder().quoteAsString(a1, a2);
    }
    
    public static byte[] quoteAsJsonUTF8(final String a1) {
        return getJsonStringEncoder().quoteAsUTF8(a1);
    }
    
    static {
        _bufferRecyclerTracker = ("true".equals(System.getProperty("com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers")) ? ThreadLocalBufferManager.instance() : null);
        _recyclerRef = new ThreadLocal<SoftReference<BufferRecycler>>();
        _encoderRef = new ThreadLocal<SoftReference<JsonStringEncoder>>();
    }
}
