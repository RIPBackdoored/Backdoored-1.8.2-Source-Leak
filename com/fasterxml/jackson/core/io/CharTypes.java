package com.fasterxml.jackson.core.io;

import java.util.*;

public final class CharTypes
{
    private static final char[] HC;
    private static final byte[] HB;
    private static final int[] sInputCodes;
    private static final int[] sInputCodesUTF8;
    private static final int[] sInputCodesJsNames;
    private static final int[] sInputCodesUtf8JsNames;
    private static final int[] sInputCodesComment;
    private static final int[] sInputCodesWS;
    private static final int[] sOutputEscapes128;
    private static final int[] sHexValues;
    
    public CharTypes() {
        super();
    }
    
    public static int[] getInputCodeLatin1() {
        return CharTypes.sInputCodes;
    }
    
    public static int[] getInputCodeUtf8() {
        return CharTypes.sInputCodesUTF8;
    }
    
    public static int[] getInputCodeLatin1JsNames() {
        return CharTypes.sInputCodesJsNames;
    }
    
    public static int[] getInputCodeUtf8JsNames() {
        return CharTypes.sInputCodesUtf8JsNames;
    }
    
    public static int[] getInputCodeComment() {
        return CharTypes.sInputCodesComment;
    }
    
    public static int[] getInputCodeWS() {
        return CharTypes.sInputCodesWS;
    }
    
    public static int[] get7BitOutputEscapes() {
        return CharTypes.sOutputEscapes128;
    }
    
    public static int charToHex(final int a1) {
        return (a1 > 127) ? -1 : CharTypes.sHexValues[a1];
    }
    
    public static void appendQuoted(final StringBuilder v-6, final String v-5) {
        final int[] sOutputEscapes128 = CharTypes.sOutputEscapes128;
        final int length = sOutputEscapes128.length;
        for (int i = 0, length2 = v-5.length(); i < length2; ++i) {
            final char a2 = v-5.charAt(i);
            if (a2 >= length || sOutputEscapes128[a2] == 0) {
                v-6.append(a2);
            }
            else {
                v-6.append('\\');
                final int v1 = sOutputEscapes128[a2];
                if (v1 < 0) {
                    v-6.append('u');
                    v-6.append('0');
                    v-6.append('0');
                    final int a3 = a2;
                    v-6.append(CharTypes.HC[a3 >> 4]);
                    v-6.append(CharTypes.HC[a3 & 0xF]);
                }
                else {
                    v-6.append((char)v1);
                }
            }
        }
    }
    
    public static char[] copyHexChars() {
        return CharTypes.HC.clone();
    }
    
    public static byte[] copyHexBytes() {
        return CharTypes.HB.clone();
    }
    
    static {
        HC = "0123456789ABCDEF".toCharArray();
        int v0 = CharTypes.HC.length;
        HB = new byte[v0];
        for (int v2 = 0; v2 < v0; ++v2) {
            CharTypes.HB[v2] = (byte)CharTypes.HC[v2];
        }
        int[] v3 = new int[256];
        for (int v2 = 0; v2 < 32; ++v2) {
            v3[v2] = -1;
        }
        v3[92] = (v3[34] = 1);
        sInputCodes = v3;
        v3 = new int[CharTypes.sInputCodes.length];
        System.arraycopy(CharTypes.sInputCodes, 0, v3, 0, v3.length);
        for (int v2 = 128; v2 < 256; ++v2) {
            int v4;
            if ((v2 & 0xE0) == 0xC0) {
                v4 = 2;
            }
            else if ((v2 & 0xF0) == 0xE0) {
                v4 = 3;
            }
            else if ((v2 & 0xF8) == 0xF0) {
                v4 = 4;
            }
            else {
                v4 = -1;
            }
            v3[v2] = v4;
        }
        sInputCodesUTF8 = v3;
        v3 = new int[256];
        Arrays.fill(v3, -1);
        for (int v2 = 33; v2 < 256; ++v2) {
            if (Character.isJavaIdentifierPart((char)v2)) {
                v3[v2] = 0;
            }
        }
        v3[64] = 0;
        v3[42] = (v3[35] = 0);
        v3[43] = (v3[45] = 0);
        sInputCodesJsNames = v3;
        v3 = new int[256];
        System.arraycopy(CharTypes.sInputCodesJsNames, 0, v3, 0, v3.length);
        Arrays.fill(v3, 128, 128, 0);
        sInputCodesUtf8JsNames = v3;
        v3 = new int[256];
        System.arraycopy(CharTypes.sInputCodesUTF8, 128, v3, 128, 128);
        Arrays.fill(v3, 0, 32, -1);
        v3[9] = 0;
        v3[10] = 10;
        v3[13] = 13;
        v3[42] = 42;
        sInputCodesComment = v3;
        v3 = new int[256];
        System.arraycopy(CharTypes.sInputCodesUTF8, 128, v3, 128, 128);
        Arrays.fill(v3, 0, 32, -1);
        v3[9] = (v3[32] = 1);
        v3[10] = 10;
        v3[13] = 13;
        v3[47] = 47;
        v3[35] = 35;
        sInputCodesWS = v3;
        v3 = new int[128];
        for (int v2 = 0; v2 < 32; ++v2) {
            v3[v2] = -1;
        }
        v3[34] = 34;
        v3[92] = 92;
        v3[8] = 98;
        v3[9] = 116;
        v3[12] = 102;
        v3[10] = 110;
        v3[13] = 114;
        sOutputEscapes128 = v3;
        Arrays.fill(sHexValues = new int[128], -1);
        for (v0 = 0; v0 < 10; ++v0) {
            CharTypes.sHexValues[48 + v0] = v0;
        }
        for (v0 = 0; v0 < 6; ++v0) {
            CharTypes.sHexValues[97 + v0] = 10 + v0;
            CharTypes.sHexValues[65 + v0] = 10 + v0;
        }
    }
}
