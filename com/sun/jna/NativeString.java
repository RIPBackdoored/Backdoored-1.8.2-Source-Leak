package com.sun.jna;

import java.nio.*;

class NativeString implements CharSequence, Comparable
{
    static final String WIDE_STRING = "--WIDE-STRING--";
    private Pointer pointer;
    private String encoding;
    
    public NativeString(final String a1) {
        this(a1, Native.getDefaultStringEncoding());
    }
    
    public NativeString(final String a1, final boolean a2) {
        this(a1, a2 ? "--WIDE-STRING--" : Native.getDefaultStringEncoding());
    }
    
    public NativeString(final WString a1) {
        this(a1.toString(), "--WIDE-STRING--");
    }
    
    public NativeString(final String v2, final String v3) {
        super();
        if (v2 == null) {
            throw new NullPointerException("String must not be null");
        }
        this.encoding = v3;
        if ("--WIDE-STRING--".equals(this.encoding)) {
            final int a1 = (v2.length() + 1) * Native.WCHAR_SIZE;
            (this.pointer = new StringMemory(a1)).setWideString(0L, v2);
        }
        else {
            final byte[] a2 = Native.getBytes(v2, v3);
            (this.pointer = new StringMemory(a2.length + 1)).write(0L, a2, 0, a2.length);
            this.pointer.setByte(a2.length, (byte)0);
        }
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof CharSequence && this.compareTo(a1) == 0;
    }
    
    @Override
    public String toString() {
        final boolean v1 = "--WIDE-STRING--".equals(this.encoding);
        String v2 = v1 ? "const wchar_t*" : "const char*";
        v2 = v2 + "(" + (v1 ? this.pointer.getWideString(0L) : this.pointer.getString(0L, this.encoding)) + ")";
        return v2;
    }
    
    public Pointer getPointer() {
        return this.pointer;
    }
    
    @Override
    public char charAt(final int a1) {
        return this.toString().charAt(a1);
    }
    
    @Override
    public int length() {
        return this.toString().length();
    }
    
    @Override
    public CharSequence subSequence(final int a1, final int a2) {
        return CharBuffer.wrap(this.toString()).subSequence(a1, a2);
    }
    
    @Override
    public int compareTo(final Object a1) {
        if (a1 == null) {
            return 1;
        }
        return this.toString().compareTo(a1.toString());
    }
    
    private class StringMemory extends Memory
    {
        final /* synthetic */ NativeString this$0;
        
        public StringMemory(final NativeString this$0, final long a1) {
            this.this$0 = this$0;
            super(a1);
        }
        
        @Override
        public String toString() {
            return this.this$0.toString();
        }
    }
}
