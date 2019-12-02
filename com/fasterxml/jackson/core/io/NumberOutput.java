package com.fasterxml.jackson.core.io;

public final class NumberOutput
{
    private static int MILLION;
    private static int BILLION;
    private static long BILLION_L;
    private static long MIN_INT_AS_LONG;
    private static long MAX_INT_AS_LONG;
    static final String SMALLEST_INT;
    static final String SMALLEST_LONG;
    private static final int[] TRIPLET_TO_CHARS;
    private static final String[] sSmallIntStrs;
    private static final String[] sSmallIntStrs2;
    
    public NumberOutput() {
        super();
    }
    
    public static int outputInt(int a2, final char[] a3, int v1) {
        if (a2 < 0) {
            if (a2 == Integer.MIN_VALUE) {
                return _outputSmallestI(a3, v1);
            }
            a3[v1++] = '-';
            a2 = -a2;
        }
        if (a2 < NumberOutput.MILLION) {
            if (a2 >= 1000) {
                final int a4 = a2 / 1000;
                a2 -= a4 * 1000;
                v1 = _leading3(a4, a3, v1);
                v1 = _full3(a2, a3, v1);
                return v1;
            }
            if (a2 < 10) {
                a3[v1] = (char)(48 + a2);
                return v1 + 1;
            }
            return _leading3(a2, a3, v1);
        }
        else {
            if (a2 >= NumberOutput.BILLION) {
                a2 -= NumberOutput.BILLION;
                if (a2 >= NumberOutput.BILLION) {
                    a2 -= NumberOutput.BILLION;
                    a3[v1++] = '2';
                }
                else {
                    a3[v1++] = '1';
                }
                return _outputFullBillion(a2, a3, v1);
            }
            int v2 = a2 / 1000;
            final int v3 = a2 - v2 * 1000;
            a2 = v2;
            v2 /= 1000;
            final int v4 = a2 - v2 * 1000;
            v1 = _leading3(v2, a3, v1);
            v1 = _full3(v4, a3, v1);
            return _full3(v3, a3, v1);
        }
    }
    
    public static int outputInt(int a2, final byte[] a3, int v1) {
        if (a2 < 0) {
            if (a2 == Integer.MIN_VALUE) {
                return _outputSmallestI(a3, v1);
            }
            a3[v1++] = 45;
            a2 = -a2;
        }
        if (a2 < NumberOutput.MILLION) {
            if (a2 < 1000) {
                if (a2 < 10) {
                    a3[v1++] = (byte)(48 + a2);
                }
                else {
                    v1 = _leading3(a2, a3, v1);
                }
            }
            else {
                final int a4 = a2 / 1000;
                a2 -= a4 * 1000;
                v1 = _leading3(a4, a3, v1);
                v1 = _full3(a2, a3, v1);
            }
            return v1;
        }
        if (a2 >= NumberOutput.BILLION) {
            a2 -= NumberOutput.BILLION;
            if (a2 >= NumberOutput.BILLION) {
                a2 -= NumberOutput.BILLION;
                a3[v1++] = 50;
            }
            else {
                a3[v1++] = 49;
            }
            return _outputFullBillion(a2, a3, v1);
        }
        int v2 = a2 / 1000;
        final int v3 = a2 - v2 * 1000;
        a2 = v2;
        v2 /= 1000;
        final int v4 = a2 - v2 * 1000;
        v1 = _leading3(v2, a3, v1);
        v1 = _full3(v4, a3, v1);
        return _full3(v3, a3, v1);
    }
    
    public static int outputLong(long a2, final char[] a3, int v1) {
        if (a2 < 0L) {
            if (a2 > NumberOutput.MIN_INT_AS_LONG) {
                return outputInt((int)a2, a3, v1);
            }
            if (a2 == Long.MIN_VALUE) {
                return _outputSmallestL(a3, v1);
            }
            a3[v1++] = '-';
            a2 = -a2;
        }
        else if (a2 <= NumberOutput.MAX_INT_AS_LONG) {
            return outputInt((int)a2, a3, v1);
        }
        long v2 = a2 / NumberOutput.BILLION_L;
        a2 -= v2 * NumberOutput.BILLION_L;
        if (v2 < NumberOutput.BILLION_L) {
            v1 = _outputUptoBillion((int)v2, a3, v1);
        }
        else {
            final long a4 = v2 / NumberOutput.BILLION_L;
            v2 -= a4 * NumberOutput.BILLION_L;
            v1 = _leading3((int)a4, a3, v1);
            v1 = _outputFullBillion((int)v2, a3, v1);
        }
        return _outputFullBillion((int)a2, a3, v1);
    }
    
    public static int outputLong(long a2, final byte[] a3, int v1) {
        if (a2 < 0L) {
            if (a2 > NumberOutput.MIN_INT_AS_LONG) {
                return outputInt((int)a2, a3, v1);
            }
            if (a2 == Long.MIN_VALUE) {
                return _outputSmallestL(a3, v1);
            }
            a3[v1++] = 45;
            a2 = -a2;
        }
        else if (a2 <= NumberOutput.MAX_INT_AS_LONG) {
            return outputInt((int)a2, a3, v1);
        }
        long v2 = a2 / NumberOutput.BILLION_L;
        a2 -= v2 * NumberOutput.BILLION_L;
        if (v2 < NumberOutput.BILLION_L) {
            v1 = _outputUptoBillion((int)v2, a3, v1);
        }
        else {
            final long a4 = v2 / NumberOutput.BILLION_L;
            v2 -= a4 * NumberOutput.BILLION_L;
            v1 = _leading3((int)a4, a3, v1);
            v1 = _outputFullBillion((int)v2, a3, v1);
        }
        return _outputFullBillion((int)a2, a3, v1);
    }
    
    public static String toString(final int v1) {
        if (v1 < NumberOutput.sSmallIntStrs.length) {
            if (v1 >= 0) {
                return NumberOutput.sSmallIntStrs[v1];
            }
            final int a1 = -v1 - 1;
            if (a1 < NumberOutput.sSmallIntStrs2.length) {
                return NumberOutput.sSmallIntStrs2[a1];
            }
        }
        return Integer.toString(v1);
    }
    
    public static String toString(final long a1) {
        if (a1 <= 0L && a1 >= -2147483648L) {
            return toString((int)a1);
        }
        return Long.toString(a1);
    }
    
    public static String toString(final double a1) {
        return Double.toString(a1);
    }
    
    public static String toString(final float a1) {
        return Float.toString(a1);
    }
    
    private static int _outputUptoBillion(final int a3, final char[] v1, int v2) {
        if (a3 >= NumberOutput.MILLION) {
            int v3 = a3 / 1000;
            final int v4 = a3 - v3 * 1000;
            final int v5 = v3 / 1000;
            v3 -= v5 * 1000;
            v2 = _leading3(v5, v1, v2);
            int v6 = NumberOutput.TRIPLET_TO_CHARS[v3];
            v1[v2++] = (char)(v6 >> 16);
            v1[v2++] = (char)(v6 >> 8 & 0x7F);
            v1[v2++] = (char)(v6 & 0x7F);
            v6 = NumberOutput.TRIPLET_TO_CHARS[v4];
            v1[v2++] = (char)(v6 >> 16);
            v1[v2++] = (char)(v6 >> 8 & 0x7F);
            v1[v2++] = (char)(v6 & 0x7F);
            return v2;
        }
        if (a3 < 1000) {
            return _leading3(a3, v1, v2);
        }
        final int a4 = a3 / 1000;
        final int a5 = a3 - a4 * 1000;
        return _outputUptoMillion(v1, v2, a4, a5);
    }
    
    private static int _outputFullBillion(final int a1, final char[] a2, int a3) {
        int v1 = a1 / 1000;
        final int v2 = a1 - v1 * 1000;
        final int v3 = v1 / 1000;
        int v4 = NumberOutput.TRIPLET_TO_CHARS[v3];
        a2[a3++] = (char)(v4 >> 16);
        a2[a3++] = (char)(v4 >> 8 & 0x7F);
        a2[a3++] = (char)(v4 & 0x7F);
        v1 -= v3 * 1000;
        v4 = NumberOutput.TRIPLET_TO_CHARS[v1];
        a2[a3++] = (char)(v4 >> 16);
        a2[a3++] = (char)(v4 >> 8 & 0x7F);
        a2[a3++] = (char)(v4 & 0x7F);
        v4 = NumberOutput.TRIPLET_TO_CHARS[v2];
        a2[a3++] = (char)(v4 >> 16);
        a2[a3++] = (char)(v4 >> 8 & 0x7F);
        a2[a3++] = (char)(v4 & 0x7F);
        return a3;
    }
    
    private static int _outputUptoBillion(final int a3, final byte[] v1, int v2) {
        if (a3 >= NumberOutput.MILLION) {
            int v3 = a3 / 1000;
            final int v4 = a3 - v3 * 1000;
            final int v5 = v3 / 1000;
            v3 -= v5 * 1000;
            v2 = _leading3(v5, v1, v2);
            int v6 = NumberOutput.TRIPLET_TO_CHARS[v3];
            v1[v2++] = (byte)(v6 >> 16);
            v1[v2++] = (byte)(v6 >> 8);
            v1[v2++] = (byte)v6;
            v6 = NumberOutput.TRIPLET_TO_CHARS[v4];
            v1[v2++] = (byte)(v6 >> 16);
            v1[v2++] = (byte)(v6 >> 8);
            v1[v2++] = (byte)v6;
            return v2;
        }
        if (a3 < 1000) {
            return _leading3(a3, v1, v2);
        }
        final int a4 = a3 / 1000;
        final int a5 = a3 - a4 * 1000;
        return _outputUptoMillion(v1, v2, a4, a5);
    }
    
    private static int _outputFullBillion(final int a1, final byte[] a2, int a3) {
        int v1 = a1 / 1000;
        final int v2 = a1 - v1 * 1000;
        final int v3 = v1 / 1000;
        v1 -= v3 * 1000;
        int v4 = NumberOutput.TRIPLET_TO_CHARS[v3];
        a2[a3++] = (byte)(v4 >> 16);
        a2[a3++] = (byte)(v4 >> 8);
        a2[a3++] = (byte)v4;
        v4 = NumberOutput.TRIPLET_TO_CHARS[v1];
        a2[a3++] = (byte)(v4 >> 16);
        a2[a3++] = (byte)(v4 >> 8);
        a2[a3++] = (byte)v4;
        v4 = NumberOutput.TRIPLET_TO_CHARS[v2];
        a2[a3++] = (byte)(v4 >> 16);
        a2[a3++] = (byte)(v4 >> 8);
        a2[a3++] = (byte)v4;
        return a3;
    }
    
    private static int _outputUptoMillion(final char[] a1, int a2, final int a3, final int a4) {
        int v1 = NumberOutput.TRIPLET_TO_CHARS[a3];
        if (a3 > 9) {
            if (a3 > 99) {
                a1[a2++] = (char)(v1 >> 16);
            }
            a1[a2++] = (char)(v1 >> 8 & 0x7F);
        }
        a1[a2++] = (char)(v1 & 0x7F);
        v1 = NumberOutput.TRIPLET_TO_CHARS[a4];
        a1[a2++] = (char)(v1 >> 16);
        a1[a2++] = (char)(v1 >> 8 & 0x7F);
        a1[a2++] = (char)(v1 & 0x7F);
        return a2;
    }
    
    private static int _outputUptoMillion(final byte[] a1, int a2, final int a3, final int a4) {
        int v1 = NumberOutput.TRIPLET_TO_CHARS[a3];
        if (a3 > 9) {
            if (a3 > 99) {
                a1[a2++] = (byte)(v1 >> 16);
            }
            a1[a2++] = (byte)(v1 >> 8);
        }
        a1[a2++] = (byte)v1;
        v1 = NumberOutput.TRIPLET_TO_CHARS[a4];
        a1[a2++] = (byte)(v1 >> 16);
        a1[a2++] = (byte)(v1 >> 8);
        a1[a2++] = (byte)v1;
        return a2;
    }
    
    private static int _leading3(final int a1, final char[] a2, int a3) {
        final int v1 = NumberOutput.TRIPLET_TO_CHARS[a1];
        if (a1 > 9) {
            if (a1 > 99) {
                a2[a3++] = (char)(v1 >> 16);
            }
            a2[a3++] = (char)(v1 >> 8 & 0x7F);
        }
        a2[a3++] = (char)(v1 & 0x7F);
        return a3;
    }
    
    private static int _leading3(final int a1, final byte[] a2, int a3) {
        final int v1 = NumberOutput.TRIPLET_TO_CHARS[a1];
        if (a1 > 9) {
            if (a1 > 99) {
                a2[a3++] = (byte)(v1 >> 16);
            }
            a2[a3++] = (byte)(v1 >> 8);
        }
        a2[a3++] = (byte)v1;
        return a3;
    }
    
    private static int _full3(final int a1, final char[] a2, int a3) {
        final int v1 = NumberOutput.TRIPLET_TO_CHARS[a1];
        a2[a3++] = (char)(v1 >> 16);
        a2[a3++] = (char)(v1 >> 8 & 0x7F);
        a2[a3++] = (char)(v1 & 0x7F);
        return a3;
    }
    
    private static int _full3(final int a1, final byte[] a2, int a3) {
        final int v1 = NumberOutput.TRIPLET_TO_CHARS[a1];
        a2[a3++] = (byte)(v1 >> 16);
        a2[a3++] = (byte)(v1 >> 8);
        a2[a3++] = (byte)v1;
        return a3;
    }
    
    private static int _outputSmallestL(final char[] a1, final int a2) {
        final int v1 = NumberOutput.SMALLEST_LONG.length();
        NumberOutput.SMALLEST_LONG.getChars(0, v1, a1, a2);
        return a2 + v1;
    }
    
    private static int _outputSmallestL(final byte[] a2, int v1) {
        for (int v2 = NumberOutput.SMALLEST_LONG.length(), a3 = 0; a3 < v2; ++a3) {
            a2[v1++] = (byte)NumberOutput.SMALLEST_LONG.charAt(a3);
        }
        return v1;
    }
    
    private static int _outputSmallestI(final char[] a1, final int a2) {
        final int v1 = NumberOutput.SMALLEST_INT.length();
        NumberOutput.SMALLEST_INT.getChars(0, v1, a1, a2);
        return a2 + v1;
    }
    
    private static int _outputSmallestI(final byte[] a2, int v1) {
        for (int v2 = NumberOutput.SMALLEST_INT.length(), a3 = 0; a3 < v2; ++a3) {
            a2[v1++] = (byte)NumberOutput.SMALLEST_INT.charAt(a3);
        }
        return v1;
    }
    
    static {
        NumberOutput.MILLION = 1000000;
        NumberOutput.BILLION = 1000000000;
        NumberOutput.BILLION_L = 1000000000L;
        NumberOutput.MIN_INT_AS_LONG = -2147483648L;
        NumberOutput.MAX_INT_AS_LONG = 0L;
        SMALLEST_INT = String.valueOf(Integer.MIN_VALUE);
        SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
        TRIPLET_TO_CHARS = new int[1000];
        int n = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                for (int v0 = 0; v0 < 10; ++v0) {
                    final int v2 = i + 48 << 16 | j + 48 << 8 | v0 + 48;
                    NumberOutput.TRIPLET_TO_CHARS[n++] = v2;
                }
            }
        }
        sSmallIntStrs = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        sSmallIntStrs2 = new String[] { "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10" };
    }
}
