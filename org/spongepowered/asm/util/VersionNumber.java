package org.spongepowered.asm.util;

import java.io.*;
import java.util.regex.*;

public final class VersionNumber implements Comparable<VersionNumber>, Serializable
{
    private static final long serialVersionUID = 1L;
    public static final VersionNumber NONE;
    private static final Pattern PATTERN;
    private final long value;
    private final String suffix;
    
    private VersionNumber() {
        super();
        this.value = 0L;
        this.suffix = "";
    }
    
    private VersionNumber(final short[] a1) {
        this(a1, null);
    }
    
    private VersionNumber(final short[] a1, final String a2) {
        super();
        this.value = pack(a1);
        this.suffix = ((a2 != null) ? a2 : "");
    }
    
    private VersionNumber(final short a1, final short a2, final short a3, final short a4) {
        this(a1, a2, a3, a4, null);
    }
    
    private VersionNumber(final short a1, final short a2, final short a3, final short a4, final String a5) {
        super();
        this.value = pack(a1, a2, a3, a4);
        this.suffix = ((a5 != null) ? a5 : "");
    }
    
    @Override
    public String toString() {
        final short[] v1 = unpack(this.value);
        return String.format("%d.%d%3$s%4$s%5$s", v1[0], v1[1], ((this.value & 0x0L) > 0L) ? String.format(".%d", v1[2]) : "", ((this.value & 0x7FFFL) > 0L) ? String.format(".%d", v1[3]) : "", this.suffix);
    }
    
    @Override
    public int compareTo(final VersionNumber a1) {
        if (a1 == null) {
            return 1;
        }
        final long v1 = this.value - a1.value;
        return (v1 > 0L) ? 1 : ((v1 < 0L) ? -1 : 0);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof VersionNumber && ((VersionNumber)a1).value == this.value;
    }
    
    @Override
    public int hashCode() {
        return (int)(this.value >> 32) ^ (int)(this.value & 0xFFFFFFFFL);
    }
    
    private static long pack(final short... a1) {
        return (long)a1[0] << 48 | (long)a1[1] << 32 | (long)(a1[2] << 16) | (long)a1[3];
    }
    
    private static short[] unpack(final long a1) {
        return new short[] { (short)(a1 >> 48), (short)(a1 >> 32 & 0x7FFFL), (short)(a1 >> 16 & 0x7FFFL), (short)(a1 & 0x7FFFL) };
    }
    
    public static VersionNumber parse(final String a1) {
        return parse(a1, VersionNumber.NONE);
    }
    
    public static VersionNumber parse(final String a1, final String a2) {
        return parse(a1, parse(a2));
    }
    
    private static VersionNumber parse(final String v-3, final VersionNumber v-2) {
        if (v-3 == null) {
            return v-2;
        }
        final Matcher matcher = VersionNumber.PATTERN.matcher(v-3);
        if (!matcher.matches()) {
            return v-2;
        }
        final short[] v0 = new short[4];
        for (int v2 = 0; v2 < 4; ++v2) {
            final String a2 = matcher.group(v2 + 1);
            if (a2 != null) {
                final int a3 = Integer.parseInt(a2);
                if (a3 > 32767) {
                    throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + a3);
                }
                v0[v2] = (short)a3;
            }
        }
        return new VersionNumber(v0, matcher.group(5));
    }
    
    @Override
    public /* bridge */ int compareTo(final Object o) {
        return this.compareTo((VersionNumber)o);
    }
    
    static {
        NONE = new VersionNumber();
        PATTERN = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
    }
}
