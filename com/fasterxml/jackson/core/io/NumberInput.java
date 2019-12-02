package com.fasterxml.jackson.core.io;

import java.math.*;

public final class NumberInput
{
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";
    static final long L_BILLION = 1000000000L;
    static final String MIN_LONG_STR_NO_SIGN;
    static final String MAX_LONG_STR;
    
    public NumberInput() {
        super();
    }
    
    public static int parseInt(final char[] a1, int a2, int a3) {
        int v1 = a1[a2] - '0';
        if (a3 > 4) {
            v1 = v1 * 10 + (a1[++a2] - '0');
            v1 = v1 * 10 + (a1[++a2] - '0');
            v1 = v1 * 10 + (a1[++a2] - '0');
            v1 = v1 * 10 + (a1[++a2] - '0');
            a3 -= 4;
            if (a3 > 4) {
                v1 = v1 * 10 + (a1[++a2] - '0');
                v1 = v1 * 10 + (a1[++a2] - '0');
                v1 = v1 * 10 + (a1[++a2] - '0');
                v1 = v1 * 10 + (a1[++a2] - '0');
                return v1;
            }
        }
        if (a3 > 1) {
            v1 = v1 * 10 + (a1[++a2] - '0');
            if (a3 > 2) {
                v1 = v1 * 10 + (a1[++a2] - '0');
                if (a3 > 3) {
                    v1 = v1 * 10 + (a1[++a2] - '0');
                }
            }
        }
        return v1;
    }
    
    public static int parseInt(final String a1) {
        char v1 = a1.charAt(0);
        final int v2 = a1.length();
        final boolean v3 = v1 == '-';
        int v4 = 1;
        if (v3) {
            if (v2 == 1 || v2 > 10) {
                return Integer.parseInt(a1);
            }
            v1 = a1.charAt(v4++);
        }
        else if (v2 > 9) {
            return Integer.parseInt(a1);
        }
        if (v1 > '9' || v1 < '0') {
            return Integer.parseInt(a1);
        }
        int v5 = v1 - '0';
        if (v4 < v2) {
            v1 = a1.charAt(v4++);
            if (v1 > '9' || v1 < '0') {
                return Integer.parseInt(a1);
            }
            v5 = v5 * 10 + (v1 - '0');
            if (v4 < v2) {
                v1 = a1.charAt(v4++);
                if (v1 > '9' || v1 < '0') {
                    return Integer.parseInt(a1);
                }
                v5 = v5 * 10 + (v1 - '0');
                if (v4 < v2) {
                    do {
                        v1 = a1.charAt(v4++);
                        if (v1 > '9' || v1 < '0') {
                            return Integer.parseInt(a1);
                        }
                        v5 = v5 * 10 + (v1 - '0');
                    } while (v4 < v2);
                }
            }
        }
        return v3 ? (-v5) : v5;
    }
    
    public static long parseLong(final char[] a1, final int a2, final int a3) {
        final int v1 = a3 - 9;
        final long v2 = parseInt(a1, a2, v1) * 1000000000L;
        return v2 + parseInt(a1, a2 + v1, 9);
    }
    
    public static long parseLong(final String a1) {
        final int v1 = a1.length();
        if (v1 <= 9) {
            return parseInt(a1);
        }
        return Long.parseLong(a1);
    }
    
    public static boolean inLongRange(final char[] a3, final int a4, final int v1, final boolean v2) {
        final String v3 = v2 ? NumberInput.MIN_LONG_STR_NO_SIGN : NumberInput.MAX_LONG_STR;
        final int v4 = v3.length();
        if (v1 < v4) {
            return true;
        }
        if (v1 > v4) {
            return false;
        }
        for (int a5 = 0; a5 < v4; ++a5) {
            final int a6 = a3[a4 + a5] - v3.charAt(a5);
            if (a6 != 0) {
                return a6 < 0;
            }
        }
        return true;
    }
    
    public static boolean inLongRange(final String v1, final boolean v2) {
        final String v3 = v2 ? NumberInput.MIN_LONG_STR_NO_SIGN : NumberInput.MAX_LONG_STR;
        final int v4 = v3.length();
        final int v5 = v1.length();
        if (v5 < v4) {
            return true;
        }
        if (v5 > v4) {
            return false;
        }
        for (int a2 = 0; a2 < v4; ++a2) {
            final int a3 = v1.charAt(a2) - v3.charAt(a2);
            if (a3 != 0) {
                return a3 < 0;
            }
        }
        return true;
    }
    
    public static int parseAsInt(String v-3, final int v-2) {
        if (v-3 == null) {
            return v-2;
        }
        v-3 = v-3.trim();
        int n = v-3.length();
        if (n == 0) {
            return v-2;
        }
        int v0 = 0;
        if (v0 < n) {
            final char a1 = v-3.charAt(0);
            if (a1 == '+') {
                v-3 = v-3.substring(1);
                n = v-3.length();
            }
            else if (a1 == '-') {
                ++v0;
            }
        }
        while (v0 < n) {
            final char v2 = v-3.charAt(v0);
            Label_0103: {
                if (v2 <= '9') {
                    if (v2 >= '0') {
                        break Label_0103;
                    }
                }
                try {
                    return (int)parseDouble(v-3);
                }
                catch (NumberFormatException a2) {
                    return v-2;
                }
            }
            ++v0;
        }
        try {
            return Integer.parseInt(v-3);
        }
        catch (NumberFormatException v3) {
            return v-2;
        }
    }
    
    public static long parseAsLong(String v-4, final long v-3) {
        if (v-4 == null) {
            return v-3;
        }
        v-4 = v-4.trim();
        int n = v-4.length();
        if (n == 0) {
            return v-3;
        }
        int v0 = 0;
        if (v0 < n) {
            final char a1 = v-4.charAt(0);
            if (a1 == '+') {
                v-4 = v-4.substring(1);
                n = v-4.length();
            }
            else if (a1 == '-') {
                ++v0;
            }
        }
        while (v0 < n) {
            final char v2 = v-4.charAt(v0);
            Label_0107: {
                if (v2 <= '9') {
                    if (v2 >= '0') {
                        break Label_0107;
                    }
                }
                try {
                    return (long)parseDouble(v-4);
                }
                catch (NumberFormatException a2) {
                    return v-3;
                }
            }
            ++v0;
        }
        try {
            return Long.parseLong(v-4);
        }
        catch (NumberFormatException v3) {
            return v-3;
        }
    }
    
    public static double parseAsDouble(String a2, final double v1) {
        if (a2 == null) {
            return v1;
        }
        a2 = a2.trim();
        final int v2 = a2.length();
        if (v2 == 0) {
            return v1;
        }
        try {
            return parseDouble(a2);
        }
        catch (NumberFormatException a3) {
            return v1;
        }
    }
    
    public static double parseDouble(final String a1) throws NumberFormatException {
        if ("2.2250738585072012e-308".equals(a1)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(a1);
    }
    
    public static BigDecimal parseBigDecimal(final String v1) throws NumberFormatException {
        try {
            return new BigDecimal(v1);
        }
        catch (NumberFormatException a1) {
            throw _badBD(v1);
        }
    }
    
    public static BigDecimal parseBigDecimal(final char[] a1) throws NumberFormatException {
        return parseBigDecimal(a1, 0, a1.length);
    }
    
    public static BigDecimal parseBigDecimal(final char[] a2, final int a3, final int v1) throws NumberFormatException {
        try {
            return new BigDecimal(a2, a3, v1);
        }
        catch (NumberFormatException a4) {
            throw _badBD(new String(a2, a3, v1));
        }
    }
    
    private static NumberFormatException _badBD(final String a1) {
        return new NumberFormatException("Value \"" + a1 + "\" can not be represented as BigDecimal");
    }
    
    static {
        MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
        MAX_LONG_STR = String.valueOf(4294967295L);
    }
}
