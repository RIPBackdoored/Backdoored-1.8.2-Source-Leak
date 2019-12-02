package com.google.api.client.repackaged.com.google.common.base;

import javax.annotation.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

@GwtCompatible
public final class Strings
{
    private Strings() {
        super();
    }
    
    public static String nullToEmpty(@Nullable final String a1) {
        return (a1 == null) ? "" : a1;
    }
    
    @Nullable
    public static String emptyToNull(@Nullable final String a1) {
        return isNullOrEmpty(a1) ? null : a1;
    }
    
    public static boolean isNullOrEmpty(@Nullable final String a1) {
        return Platform.stringIsNullOrEmpty(a1);
    }
    
    public static String padStart(final String a2, final int a3, final char v1) {
        Preconditions.checkNotNull(a2);
        if (a2.length() >= a3) {
            return a2;
        }
        final StringBuilder v2 = new StringBuilder(a3);
        for (int a4 = a2.length(); a4 < a3; ++a4) {
            v2.append(v1);
        }
        v2.append(a2);
        return v2.toString();
    }
    
    public static String padEnd(final String a2, final int a3, final char v1) {
        Preconditions.checkNotNull(a2);
        if (a2.length() >= a3) {
            return a2;
        }
        final StringBuilder v2 = new StringBuilder(a3);
        v2.append(a2);
        for (int a4 = a2.length(); a4 < a3; ++a4) {
            v2.append(v1);
        }
        return v2.toString();
    }
    
    public static String repeat(final String a1, final int a2) {
        Preconditions.checkNotNull(a1);
        if (a2 <= 1) {
            Preconditions.checkArgument(a2 >= 0, "invalid count: %s", a2);
            return (a2 == 0) ? "" : a1;
        }
        final int v1 = a1.length();
        final long v2 = v1 * (long)a2;
        final int v3 = (int)v2;
        if (v3 != v2) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + v2);
        }
        final char[] v4 = new char[v3];
        a1.getChars(0, v1, v4, 0);
        int v5;
        for (v5 = v1; v5 < v3 - v5; v5 <<= 1) {
            System.arraycopy(v4, 0, v4, v5, v5);
        }
        System.arraycopy(v4, 0, v4, v5, v3 - v5);
        return new String(v4);
    }
    
    public static String commonPrefix(final CharSequence a1, final CharSequence a2) {
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        int v1;
        int v2;
        for (v1 = Math.min(a1.length(), a2.length()), v2 = 0; v2 < v1 && a1.charAt(v2) == a2.charAt(v2); ++v2) {}
        if (validSurrogatePairAt(a1, v2 - 1) || validSurrogatePairAt(a2, v2 - 1)) {
            --v2;
        }
        return a1.subSequence(0, v2).toString();
    }
    
    public static String commonSuffix(final CharSequence a1, final CharSequence a2) {
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        int v1;
        int v2;
        for (v1 = Math.min(a1.length(), a2.length()), v2 = 0; v2 < v1 && a1.charAt(a1.length() - v2 - 1) == a2.charAt(a2.length() - v2 - 1); ++v2) {}
        if (validSurrogatePairAt(a1, a1.length() - v2 - 1) || validSurrogatePairAt(a2, a2.length() - v2 - 1)) {
            --v2;
        }
        return a1.subSequence(a1.length() - v2, a1.length()).toString();
    }
    
    @VisibleForTesting
    static boolean validSurrogatePairAt(final CharSequence a1, final int a2) {
        return a2 >= 0 && a2 <= a1.length() - 2 && Character.isHighSurrogate(a1.charAt(a2)) && Character.isLowSurrogate(a1.charAt(a2 + 1));
    }
}
