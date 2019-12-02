package com.sun.jna;

import java.util.*;

public class StringArray extends Memory implements Function.PostCallRead
{
    private String encoding;
    private List<NativeString> natives;
    private Object[] original;
    
    public StringArray(final String[] a1) {
        this(a1, false);
    }
    
    public StringArray(final String[] a1, final boolean a2) {
        this((Object[])a1, a2 ? "--WIDE-STRING--" : Native.getDefaultStringEncoding());
    }
    
    public StringArray(final String[] a1, final String a2) {
        this((Object[])a1, a2);
    }
    
    public StringArray(final WString[] a1) {
        this(a1, "--WIDE-STRING--");
    }
    
    private StringArray(final Object[] v-1, final String v0) {
        super((v-1.length + 1) * Pointer.SIZE);
        this.natives = new ArrayList<NativeString>();
        this.original = v-1;
        this.encoding = v0;
        for (int v = 0; v < v-1.length; ++v) {
            Pointer a2 = null;
            if (v-1[v] != null) {
                final NativeString a3 = new NativeString(v-1[v].toString(), v0);
                this.natives.add(a3);
                a2 = a3.getPointer();
            }
            this.setPointer(Pointer.SIZE * v, a2);
        }
        this.setPointer(Pointer.SIZE * v-1.length, null);
    }
    
    @Override
    public void read() {
        final boolean b = this.original instanceof WString[];
        final boolean equals = "--WIDE-STRING--".equals(this.encoding);
        for (int v0 = 0; v0 < this.original.length; ++v0) {
            final Pointer v2 = this.getPointer(v0 * Pointer.SIZE);
            Object v3 = null;
            if (v2 != null) {
                v3 = (equals ? v2.getWideString(0L) : v2.getString(0L, this.encoding));
                if (b) {
                    v3 = new WString((String)v3);
                }
            }
            this.original[v0] = v3;
        }
    }
    
    @Override
    public String toString() {
        final boolean v1 = "--WIDE-STRING--".equals(this.encoding);
        String v2 = v1 ? "const wchar_t*[]" : "const char*[]";
        v2 += Arrays.asList(this.original);
        return v2;
    }
}
