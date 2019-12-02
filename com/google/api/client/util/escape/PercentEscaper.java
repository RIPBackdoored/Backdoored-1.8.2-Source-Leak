package com.google.api.client.util.escape;

public class PercentEscaper extends UnicodeEscaper
{
    public static final String SAFECHARS_URLENCODER = "-_.*";
    public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=";
    public static final String SAFE_PLUS_RESERVED_CHARS_URLENCODER = "-_.!~*'()@:$&,;=+/?";
    public static final String SAFEUSERINFOCHARS_URLENCODER = "-_.!~*'():$&,;=";
    public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
    private static final char[] URI_ESCAPED_SPACE;
    private static final char[] UPPER_HEX_DIGITS;
    private final boolean plusForSpace;
    private final boolean[] safeOctets;
    
    public PercentEscaper(final String a1, final boolean a2) {
        super();
        if (a1.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        if (a2 && a1.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        if (a1.contains("%")) {
            throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
        }
        this.plusForSpace = a2;
        this.safeOctets = createSafeOctets(a1);
    }
    
    private static boolean[] createSafeOctets(final String v-3) {
        int max = 122;
        final char[] charArray;
        final char[] array = charArray = v-3.toCharArray();
        for (final char a1 : charArray) {
            max = Math.max(a1, max);
        }
        final boolean[] v0 = new boolean[max + 1];
        for (int v2 = 48; v2 <= 57; ++v2) {
            v0[v2] = true;
        }
        for (int v2 = 65; v2 <= 90; ++v2) {
            v0[v2] = true;
        }
        for (int v2 = 97; v2 <= 122; ++v2) {
            v0[v2] = true;
        }
        for (final char v3 : array) {
            v0[v3] = true;
        }
        return v0;
    }
    
    @Override
    protected int nextEscapeIndex(final CharSequence a3, int v1, final int v2) {
        while (v1 < v2) {
            final char a4 = a3.charAt(v1);
            if (a4 >= this.safeOctets.length) {
                break;
            }
            if (!this.safeOctets[a4]) {
                break;
            }
            ++v1;
        }
        return v1;
    }
    
    @Override
    public String escape(final String v-1) {
        for (int v0 = v-1.length(), v2 = 0; v2 < v0; ++v2) {
            final char a1 = v-1.charAt(v2);
            if (a1 >= this.safeOctets.length || !this.safeOctets[a1]) {
                return this.escapeSlow(v-1, v2);
            }
        }
        return v-1;
    }
    
    @Override
    protected char[] escape(int v0) {
        if (v0 < this.safeOctets.length && this.safeOctets[v0]) {
            return null;
        }
        if (v0 == 32 && this.plusForSpace) {
            return PercentEscaper.URI_ESCAPED_SPACE;
        }
        if (v0 <= 127) {
            final char[] a1 = { '%', PercentEscaper.UPPER_HEX_DIGITS[v0 >>> 4], PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF] };
            return a1;
        }
        if (v0 <= 2047) {
            final char[] v = { '%', '\0', '\0', '%', '\0', PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF] };
            v0 >>>= 4;
            v[4] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (v0 & 0x3)];
            v0 >>>= 2;
            v[2] = PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF];
            v0 >>>= 4;
            v[1] = PercentEscaper.UPPER_HEX_DIGITS[0xC | v0];
            return v;
        }
        if (v0 <= 65535) {
            final char[] v = { '%', 'E', '\0', '%', '\0', '\0', '%', '\0', PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF] };
            v0 >>>= 4;
            v[7] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (v0 & 0x3)];
            v0 >>>= 2;
            v[5] = PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF];
            v0 >>>= 4;
            v[4] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (v0 & 0x3)];
            v0 >>>= 2;
            v[2] = PercentEscaper.UPPER_HEX_DIGITS[v0];
            return v;
        }
        if (v0 <= 1114111) {
            final char[] v = { '%', 'F', '\0', '%', '\0', '\0', '%', '\0', '\0', '%', '\0', PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF] };
            v0 >>>= 4;
            v[10] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (v0 & 0x3)];
            v0 >>>= 2;
            v[8] = PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF];
            v0 >>>= 4;
            v[7] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (v0 & 0x3)];
            v0 >>>= 2;
            v[5] = PercentEscaper.UPPER_HEX_DIGITS[v0 & 0xF];
            v0 >>>= 4;
            v[4] = PercentEscaper.UPPER_HEX_DIGITS[0x8 | (v0 & 0x3)];
            v0 >>>= 2;
            v[2] = PercentEscaper.UPPER_HEX_DIGITS[v0 & 0x7];
            return v;
        }
        throw new IllegalArgumentException("Invalid unicode character value " + v0);
    }
    
    static {
        URI_ESCAPED_SPACE = new char[] { '+' };
        UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    }
}
