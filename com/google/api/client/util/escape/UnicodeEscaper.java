package com.google.api.client.util.escape;

public abstract class UnicodeEscaper extends Escaper
{
    private static final int DEST_PAD = 32;
    
    public UnicodeEscaper() {
        super();
    }
    
    protected abstract char[] escape(final int p0);
    
    protected abstract int nextEscapeIndex(final CharSequence p0, final int p1, final int p2);
    
    @Override
    public abstract String escape(final String p0);
    
    protected final String escapeSlow(final String v-9, int v-8) {
        final int length = v-9.length();
        char[] array = Platform.charBufferFromThreadLocal();
        int n = 0;
        int n2 = 0;
        while (v-8 < length) {
            final int codePoint = codePointAt(v-9, v-8, length);
            if (codePoint < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            final char[] escape = this.escape(codePoint);
            final int n3 = v-8 + (Character.isSupplementaryCodePoint(codePoint) ? 2 : 1);
            if (escape != null) {
                final int a2 = v-8 - n2;
                final int v1 = n + a2 + escape.length;
                if (array.length < v1) {
                    final int a3 = v1 + length - v-8 + 32;
                    array = growBuffer(array, n, a3);
                }
                if (a2 > 0) {
                    v-9.getChars(n2, v-8, array, n);
                    n += a2;
                }
                if (escape.length > 0) {
                    System.arraycopy(escape, 0, array, n, escape.length);
                    n += escape.length;
                }
                n2 = n3;
            }
            v-8 = this.nextEscapeIndex(v-9, n3, length);
        }
        final int codePoint = length - n2;
        if (codePoint > 0) {
            final int a4 = n + codePoint;
            if (array.length < a4) {
                array = growBuffer(array, n, a4);
            }
            v-9.getChars(n2, length, array, n);
            n = a4;
        }
        return new String(array, 0, n);
    }
    
    protected static int codePointAt(final CharSequence a3, int v1, final int v2) {
        if (v1 >= v2) {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        final char a4 = a3.charAt(v1++);
        if (a4 < '\ud800' || a4 > '\udfff') {
            return a4;
        }
        if (a4 > '\udbff') {
            throw new IllegalArgumentException("Unexpected low surrogate character '" + a4 + "' with value " + (int)a4 + " at index " + (v1 - 1));
        }
        if (v1 == v2) {
            return -a4;
        }
        final char a5 = a3.charAt(v1);
        if (Character.isLowSurrogate(a5)) {
            return Character.toCodePoint(a4, a5);
        }
        throw new IllegalArgumentException("Expected low surrogate but got char '" + a5 + "' with value " + (int)a5 + " at index " + v1);
    }
    
    private static char[] growBuffer(final char[] a1, final int a2, final int a3) {
        final char[] v1 = new char[a3];
        if (a2 > 0) {
            System.arraycopy(a1, 0, v1, 0, a2);
        }
        return v1;
    }
}
