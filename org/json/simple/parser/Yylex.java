package org.json.simple.parser;

import java.io.*;

class Yylex
{
    public static final int YYEOF = -1;
    private static final int ZZ_BUFFERSIZE = 16384;
    public static final int YYINITIAL = 0;
    public static final int STRING_BEGIN = 2;
    private static final int[] ZZ_LEXSTATE;
    private static final String ZZ_CMAP_PACKED = "\t\u0000\u0001\u0007\u0001\u0007\u0002\u0000\u0001\u0007\u0012\u0000\u0001\u0007\u0001\u0000\u0001\t\b\u0000\u0001\u0006\u0001\u0019\u0001\u0002\u0001\u0004\u0001\n\n\u0003\u0001\u001a\u0006\u0000\u0004\u0001\u0001\u0005\u0001\u0001\u0014\u0000\u0001\u0017\u0001\b\u0001\u0018\u0003\u0000\u0001\u0012\u0001\u000b\u0002\u0001\u0001\u0011\u0001\f\u0005\u0000\u0001\u0013\u0001\u0000\u0001\r\u0003\u0000\u0001\u000e\u0001\u0014\u0001\u000f\u0001\u0010\u0005\u0000\u0001\u0015\u0001\u0000\u0001\u0016\uff82\u0000";
    private static final char[] ZZ_CMAP;
    private static final int[] ZZ_ACTION;
    private static final String ZZ_ACTION_PACKED_0 = "\u0002\u0000\u0002\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0003\u0001\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0005\u0000\u0001\f\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0000\u0001\u0015\u0004\u0000\u0001\u0016\u0001\u0017\u0002\u0000\u0001\u0018";
    private static final int[] ZZ_ROWMAP;
    private static final String ZZ_ROWMAP_PACKED_0 = "\u0000\u0000\u0000\u001b\u00006\u0000Q\u0000l\u0000\u0087\u00006\u0000¢\u0000½\u0000\u00d8\u00006\u00006\u00006\u00006\u00006\u00006\u0000\u00f3\u0000\u010e\u00006\u0000\u0129\u0000\u0144\u0000\u015f\u0000\u017a\u0000\u0195\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u0000\u01b0\u0000\u01cb\u0000\u01e6\u0000\u01e6\u0000\u0201\u0000\u021c\u0000\u0237\u0000\u0252\u00006\u00006\u0000\u026d\u0000\u0288\u00006";
    private static final int[] ZZ_TRANS;
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;
    private static final String[] ZZ_ERROR_MSG;
    private static final int[] ZZ_ATTRIBUTE;
    private static final String ZZ_ATTRIBUTE_PACKED_0 = "\u0002\u0000\u0001\t\u0003\u0001\u0001\t\u0003\u0001\u0006\t\u0002\u0001\u0001\t\u0005\u0000\b\t\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0004\u0000\u0002\t\u0002\u0000\u0001\t";
    private Reader zzReader;
    private int zzState;
    private int zzLexicalState;
    private char[] zzBuffer;
    private int zzMarkedPos;
    private int zzCurrentPos;
    private int zzStartRead;
    private int zzEndRead;
    private int yyline;
    private int yychar;
    private int yycolumn;
    private boolean zzAtBOL;
    private boolean zzAtEOF;
    private StringBuffer sb;
    
    private static int[] zzUnpackAction() {
        final int[] v1 = new int[45];
        int v2 = 0;
        v2 = zzUnpackAction("\u0002\u0000\u0002\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0003\u0001\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0005\u0000\u0001\f\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0000\u0001\u0015\u0004\u0000\u0001\u0016\u0001\u0017\u0002\u0000\u0001\u0018", v2, v1);
        return v1;
    }
    
    private static int zzUnpackAction(final String a3, final int v1, final int[] v2) {
        int v3 = 0;
        int v4 = v1;
        final int v5 = a3.length();
        while (v3 < v5) {
            int a4 = a3.charAt(v3++);
            final int a5 = a3.charAt(v3++);
            do {
                v2[v4++] = a5;
            } while (--a4 > 0);
        }
        return v4;
    }
    
    private static int[] zzUnpackRowMap() {
        final int[] v1 = new int[45];
        int v2 = 0;
        v2 = zzUnpackRowMap("\u0000\u0000\u0000\u001b\u00006\u0000Q\u0000l\u0000\u0087\u00006\u0000¢\u0000½\u0000\u00d8\u00006\u00006\u00006\u00006\u00006\u00006\u0000\u00f3\u0000\u010e\u00006\u0000\u0129\u0000\u0144\u0000\u015f\u0000\u017a\u0000\u0195\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u0000\u01b0\u0000\u01cb\u0000\u01e6\u0000\u01e6\u0000\u0201\u0000\u021c\u0000\u0237\u0000\u0252\u00006\u00006\u0000\u026d\u0000\u0288\u00006", v2, v1);
        return v1;
    }
    
    private static int zzUnpackRowMap(final String a2, final int a3, final int[] v1) {
        int v2 = 0;
        int v3 = a3;
        int a4;
        for (int v4 = a2.length(); v2 < v4; a4 = a2.charAt(v2++) << 16, v1[v3++] = (a4 | a2.charAt(v2++))) {}
        return v3;
    }
    
    private static int[] zzUnpackAttribute() {
        final int[] v1 = new int[45];
        int v2 = 0;
        v2 = zzUnpackAttribute("\u0002\u0000\u0001\t\u0003\u0001\u0001\t\u0003\u0001\u0006\t\u0002\u0001\u0001\t\u0005\u0000\b\t\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0004\u0000\u0002\t\u0002\u0000\u0001\t", v2, v1);
        return v1;
    }
    
    private static int zzUnpackAttribute(final String a3, final int v1, final int[] v2) {
        int v3 = 0;
        int v4 = v1;
        final int v5 = a3.length();
        while (v3 < v5) {
            int a4 = a3.charAt(v3++);
            final int a5 = a3.charAt(v3++);
            do {
                v2[v4++] = a5;
            } while (--a4 > 0);
        }
        return v4;
    }
    
    int getPosition() {
        return this.yychar;
    }
    
    Yylex(final Reader a1) {
        super();
        this.zzLexicalState = 0;
        this.zzBuffer = new char[16384];
        this.zzAtBOL = true;
        this.sb = new StringBuffer();
        this.zzReader = a1;
    }
    
    Yylex(final InputStream a1) {
        this(new InputStreamReader(a1));
    }
    
    private static char[] zzUnpackCMap(final String v-4) {
        final char[] array = new char[65536];
        int i = 0;
        int n = 0;
        while (i < 90) {
            int a1 = v-4.charAt(i++);
            final char v1 = v-4.charAt(i++);
            do {
                array[n++] = v1;
            } while (--a1 > 0);
        }
        return array;
    }
    
    private boolean zzRefill() throws IOException {
        if (this.zzStartRead > 0) {
            System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);
            this.zzEndRead -= this.zzStartRead;
            this.zzCurrentPos -= this.zzStartRead;
            this.zzMarkedPos -= this.zzStartRead;
            this.zzStartRead = 0;
        }
        if (this.zzCurrentPos >= this.zzBuffer.length) {
            final char[] v1 = new char[this.zzCurrentPos * 2];
            System.arraycopy(this.zzBuffer, 0, v1, 0, this.zzBuffer.length);
            this.zzBuffer = v1;
        }
        final int v2 = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);
        if (v2 > 0) {
            this.zzEndRead += v2;
            return false;
        }
        if (v2 != 0) {
            return true;
        }
        final int v3 = this.zzReader.read();
        if (v3 == -1) {
            return true;
        }
        this.zzBuffer[this.zzEndRead++] = (char)v3;
        return false;
    }
    
    public final void yyclose() throws IOException {
        this.zzAtEOF = true;
        this.zzEndRead = this.zzStartRead;
        if (this.zzReader != null) {
            this.zzReader.close();
        }
    }
    
    public final void yyreset(final Reader a1) {
        this.zzReader = a1;
        this.zzAtBOL = true;
        this.zzAtEOF = false;
        final int n = 0;
        this.zzStartRead = n;
        this.zzEndRead = n;
        final int n2 = 0;
        this.zzMarkedPos = n2;
        this.zzCurrentPos = n2;
        final int yyline = 0;
        this.yycolumn = yyline;
        this.yychar = yyline;
        this.yyline = yyline;
        this.zzLexicalState = 0;
    }
    
    public final int yystate() {
        return this.zzLexicalState;
    }
    
    public final void yybegin(final int a1) {
        this.zzLexicalState = a1;
    }
    
    public final String yytext() {
        return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
    }
    
    public final char yycharat(final int a1) {
        return this.zzBuffer[this.zzStartRead + a1];
    }
    
    public final int yylength() {
        return this.zzMarkedPos - this.zzStartRead;
    }
    
    private void zzScanError(final int v2) {
        String v3;
        try {
            v3 = Yylex.ZZ_ERROR_MSG[v2];
        }
        catch (ArrayIndexOutOfBoundsException a1) {
            v3 = Yylex.ZZ_ERROR_MSG[0];
        }
        throw new Error(v3);
    }
    
    public void yypushback(final int a1) {
        if (a1 > this.yylength()) {
            this.zzScanError(2);
        }
        this.zzMarkedPos -= a1;
    }
    
    public Yytoken yylex() throws IOException, ParseException {
        int v5 = this.zzEndRead;
        char[] v6 = this.zzBuffer;
        final char[] v7 = Yylex.ZZ_CMAP;
        final int[] v8 = Yylex.ZZ_TRANS;
        final int[] v9 = Yylex.ZZ_ROWMAP;
        final int[] v10 = Yylex.ZZ_ATTRIBUTE;
        while (true) {
            int v11 = this.zzMarkedPos;
            this.yychar += v11 - this.zzStartRead;
            int v12 = -1;
            final int n = v11;
            this.zzStartRead = n;
            this.zzCurrentPos = n;
            int v13 = n;
            this.zzState = Yylex.ZZ_LEXSTATE[this.zzLexicalState];
            int v14;
            while (true) {
                if (v13 < v5) {
                    v14 = v6[v13++];
                }
                else {
                    if (this.zzAtEOF) {
                        v14 = -1;
                        break;
                    }
                    this.zzCurrentPos = v13;
                    this.zzMarkedPos = v11;
                    final boolean v15 = this.zzRefill();
                    v13 = this.zzCurrentPos;
                    v11 = this.zzMarkedPos;
                    v6 = this.zzBuffer;
                    v5 = this.zzEndRead;
                    if (v15) {
                        v14 = -1;
                        break;
                    }
                    v14 = v6[v13++];
                }
                final int v16 = v8[v9[this.zzState] + v7[v14]];
                if (v16 == -1) {
                    break;
                }
                this.zzState = v16;
                final int v17 = v10[this.zzState];
                if ((v17 & 0x1) != 0x1) {
                    continue;
                }
                v12 = this.zzState;
                v11 = v13;
                if ((v17 & 0x8) == 0x8) {
                    break;
                }
            }
            this.zzMarkedPos = v11;
            switch ((v12 < 0) ? v12 : Yylex.ZZ_ACTION[v12]) {
                case 11: {
                    this.sb.append(this.yytext());
                }
                case 25: {
                    continue;
                }
                case 4: {
                    this.sb.delete(0, this.sb.length());
                    this.yybegin(2);
                }
                case 26: {
                    continue;
                }
                case 16: {
                    this.sb.append('\b');
                }
                case 27: {
                    continue;
                }
                case 6: {
                    return new Yytoken(2, null);
                }
                case 28: {
                    continue;
                }
                case 23: {
                    final Boolean v18 = Boolean.valueOf(this.yytext());
                    return new Yytoken(0, v18);
                }
                case 29: {
                    continue;
                }
                case 22: {
                    return new Yytoken(0, null);
                }
                case 30: {
                    continue;
                }
                case 13: {
                    this.yybegin(0);
                    return new Yytoken(0, this.sb.toString());
                }
                case 31: {
                    continue;
                }
                case 12: {
                    this.sb.append('\\');
                }
                case 32: {
                    continue;
                }
                case 21: {
                    final Double v19 = Double.valueOf(this.yytext());
                    return new Yytoken(0, v19);
                }
                case 33: {
                    continue;
                }
                case 1: {
                    throw new ParseException(this.yychar, 0, new Character(this.yycharat(0)));
                }
                case 34: {
                    continue;
                }
                case 8: {
                    return new Yytoken(4, null);
                }
                case 35: {
                    continue;
                }
                case 19: {
                    this.sb.append('\r');
                }
                case 36: {
                    continue;
                }
                case 15: {
                    this.sb.append('/');
                }
                case 37: {
                    continue;
                }
                case 10: {
                    return new Yytoken(6, null);
                }
                case 38: {
                    continue;
                }
                case 14: {
                    this.sb.append('\"');
                }
                case 39: {
                    continue;
                }
                case 5: {
                    return new Yytoken(1, null);
                }
                case 40: {
                    continue;
                }
                case 17: {
                    this.sb.append('\f');
                }
                case 41: {
                    continue;
                }
                case 24: {
                    try {
                        final int v16 = Integer.parseInt(this.yytext().substring(2), 16);
                        this.sb.append((char)v16);
                    }
                    catch (Exception v20) {
                        throw new ParseException(this.yychar, 2, v20);
                    }
                }
                case 42: {
                    continue;
                }
                case 20: {
                    this.sb.append('\t');
                }
                case 43: {
                    continue;
                }
                case 7: {
                    return new Yytoken(3, null);
                }
                case 44: {
                    continue;
                }
                case 2: {
                    final Long v21 = Long.valueOf(this.yytext());
                    return new Yytoken(0, v21);
                }
                case 45: {
                    continue;
                }
                case 18: {
                    this.sb.append('\n');
                }
                case 46: {
                    continue;
                }
                case 9: {
                    return new Yytoken(5, null);
                }
                case 47: {
                    continue;
                }
                case 3:
                case 48: {
                    continue;
                }
                default: {
                    if (v14 == -1 && this.zzStartRead == this.zzCurrentPos) {
                        this.zzAtEOF = true;
                        return null;
                    }
                    this.zzScanError(1);
                    continue;
                }
            }
        }
    }
    
    static {
        ZZ_LEXSTATE = new int[] { 0, 0, 1, 1 };
        ZZ_CMAP = zzUnpackCMap("\t\u0000\u0001\u0007\u0001\u0007\u0002\u0000\u0001\u0007\u0012\u0000\u0001\u0007\u0001\u0000\u0001\t\b\u0000\u0001\u0006\u0001\u0019\u0001\u0002\u0001\u0004\u0001\n\n\u0003\u0001\u001a\u0006\u0000\u0004\u0001\u0001\u0005\u0001\u0001\u0014\u0000\u0001\u0017\u0001\b\u0001\u0018\u0003\u0000\u0001\u0012\u0001\u000b\u0002\u0001\u0001\u0011\u0001\f\u0005\u0000\u0001\u0013\u0001\u0000\u0001\r\u0003\u0000\u0001\u000e\u0001\u0014\u0001\u000f\u0001\u0010\u0005\u0000\u0001\u0015\u0001\u0000\u0001\u0016\uff82\u0000");
        ZZ_ACTION = zzUnpackAction();
        ZZ_ROWMAP = zzUnpackRowMap();
        ZZ_TRANS = new int[] { 2, 2, 3, 4, 2, 2, 2, 5, 2, 6, 2, 2, 7, 8, 2, 9, 2, 2, 2, 2, 2, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 17, 18, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 19, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, 24, 25, 26, 27, 28, 29, 30, 31, 32, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 34, 35, -1, -1, 34, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 39, -1, 39, -1, 39, -1, -1, -1, -1, -1, 39, 39, -1, -1, -1, -1, 39, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 42, -1, 42, -1, 42, -1, -1, -1, -1, -1, 42, 42, -1, -1, -1, -1, 42, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, 43, -1, 43, -1, 43, -1, -1, -1, -1, -1, 43, 43, -1, -1, -1, -1, 43, 43, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, -1, 44, -1, 44, -1, -1, -1, -1, -1, 44, 44, -1, -1, -1, -1, 44, 44, -1, -1, -1, -1, -1, -1, -1, -1 };
        ZZ_ERROR_MSG = new String[] { "Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large" };
        ZZ_ATTRIBUTE = zzUnpackAttribute();
    }
}
