package javassist.compiler;

public class Lex implements TokenId
{
    private int lastChar;
    private StringBuffer textBuffer;
    private Token currentToken;
    private Token lookAheadTokens;
    private String input;
    private int position;
    private int maxlen;
    private int lineNumber;
    private static final int[] equalOps;
    private static final KeywordTable ktable;
    
    public Lex(final String a1) {
        super();
        this.lastChar = -1;
        this.textBuffer = new StringBuffer();
        this.currentToken = new Token();
        this.lookAheadTokens = null;
        this.input = a1;
        this.position = 0;
        this.maxlen = a1.length();
        this.lineNumber = 0;
    }
    
    public int get() {
        if (this.lookAheadTokens == null) {
            return this.get(this.currentToken);
        }
        final Token v1 = this.currentToken = this.lookAheadTokens;
        this.lookAheadTokens = this.lookAheadTokens.next;
        return v1.tokenId;
    }
    
    public int lookAhead() {
        return this.lookAhead(0);
    }
    
    public int lookAhead(int v2) {
        Token v3 = this.lookAheadTokens;
        if (v3 == null) {
            v3 = (this.lookAheadTokens = this.currentToken);
            v3.next = null;
            this.get(v3);
        }
        while (v2-- > 0) {
            if (v3.next == null) {
                final Token a1 = v3.next = new Token();
                this.get(a1);
            }
            v3 = v3.next;
        }
        this.currentToken = v3;
        return v3.tokenId;
    }
    
    public String getString() {
        return this.currentToken.textValue;
    }
    
    public long getLong() {
        return this.currentToken.longValue;
    }
    
    public double getDouble() {
        return this.currentToken.doubleValue;
    }
    
    private int get(final Token a1) {
        int v1;
        do {
            v1 = this.readLine(a1);
        } while (v1 == 10);
        return a1.tokenId = v1;
    }
    
    private int readLine(final Token v2) {
        int v3 = this.getNextNonWhiteChar();
        if (v3 < 0) {
            return v3;
        }
        if (v3 == 10) {
            ++this.lineNumber;
            return 10;
        }
        if (v3 == 39) {
            return this.readCharConst(v2);
        }
        if (v3 == 34) {
            return this.readStringL(v2);
        }
        if (48 <= v3 && v3 <= 57) {
            return this.readNumber(v3, v2);
        }
        if (v3 == 46) {
            v3 = this.getc();
            if (48 <= v3 && v3 <= 57) {
                final StringBuffer a1 = this.textBuffer;
                a1.setLength(0);
                a1.append('.');
                return this.readDouble(a1, v3, v2);
            }
            this.ungetc(v3);
            return this.readSeparator(46);
        }
        else {
            if (Character.isJavaIdentifierStart((char)v3)) {
                return this.readIdentifier(v3, v2);
            }
            return this.readSeparator(v3);
        }
    }
    
    private int getNextNonWhiteChar() {
        int v1;
        do {
            v1 = this.getc();
            if (v1 == 47) {
                v1 = this.getc();
                if (v1 == 47) {
                    do {
                        v1 = this.getc();
                        if (v1 != 10 && v1 != 13) {
                            continue;
                        }
                        break;
                    } while (v1 != -1);
                }
                else if (v1 == 42) {
                    while (true) {
                        v1 = this.getc();
                        if (v1 == -1) {
                            break;
                        }
                        if (v1 != 42) {
                            continue;
                        }
                        if ((v1 = this.getc()) == 47) {
                            v1 = 32;
                            break;
                        }
                        this.ungetc(v1);
                    }
                }
                else {
                    this.ungetc(v1);
                    v1 = 47;
                }
            }
        } while (isBlank(v1));
        return v1;
    }
    
    private int readCharConst(final Token a1) {
        int v2 = 0;
        int v3;
        while ((v3 = this.getc()) != 39) {
            if (v3 == 92) {
                v2 = this.readEscapeChar();
            }
            else {
                if (v3 < 32) {
                    if (v3 == 10) {
                        ++this.lineNumber;
                    }
                    return 500;
                }
                v2 = v3;
            }
        }
        a1.longValue = v2;
        return 401;
    }
    
    private int readEscapeChar() {
        int v1 = this.getc();
        if (v1 == 110) {
            v1 = 10;
        }
        else if (v1 == 116) {
            v1 = 9;
        }
        else if (v1 == 114) {
            v1 = 13;
        }
        else if (v1 == 102) {
            v1 = 12;
        }
        else if (v1 == 10) {
            ++this.lineNumber;
        }
        return v1;
    }
    
    private int readStringL(final Token a1) {
        final StringBuffer v2 = this.textBuffer;
        v2.setLength(0);
        while (true) {
            int v3;
            if ((v3 = this.getc()) != 34) {
                if (v3 == 92) {
                    v3 = this.readEscapeChar();
                }
                else if (v3 == 10 || v3 < 0) {
                    ++this.lineNumber;
                    return 500;
                }
                v2.append((char)v3);
            }
            else {
                while (true) {
                    v3 = this.getc();
                    if (v3 == 10) {
                        ++this.lineNumber;
                    }
                    else {
                        if (!isBlank(v3)) {
                            break;
                        }
                        continue;
                    }
                }
                if (v3 != 34) {
                    this.ungetc(v3);
                    a1.textValue = v2.toString();
                    return 406;
                }
                continue;
            }
        }
    }
    
    private int readNumber(int v1, final Token v2) {
        long v3 = 0L;
        int v4 = this.getc();
        if (v1 == 48) {
            if (v4 == 88 || v4 == 120) {
                while (true) {
                    v1 = this.getc();
                    if (48 <= v1 && v1 <= 57) {
                        v3 = v3 * 16L + (v1 - 48);
                    }
                    else if (65 <= v1 && v1 <= 70) {
                        v3 = v3 * 16L + (v1 - 65 + 10);
                    }
                    else {
                        if (97 > v1 || v1 > 102) {
                            break;
                        }
                        v3 = v3 * 16L + (v1 - 97 + 10);
                    }
                }
                v2.longValue = v3;
                if (v1 == 76 || v1 == 108) {
                    return 403;
                }
                this.ungetc(v1);
                return 402;
            }
            else if (48 <= v4 && v4 <= 55) {
                v3 = v4 - 48;
                while (true) {
                    v1 = this.getc();
                    if (48 > v1 || v1 > 55) {
                        break;
                    }
                    v3 = v3 * 8L + (v1 - 48);
                }
                v2.longValue = v3;
                if (v1 == 76 || v1 == 108) {
                    return 403;
                }
                this.ungetc(v1);
                return 402;
            }
        }
        v3 = v1 - 48;
        while (48 <= v4 && v4 <= 57) {
            v3 = v3 * 10L + v4 - 48L;
            v4 = this.getc();
        }
        v2.longValue = v3;
        if (v4 == 70 || v4 == 102) {
            v2.doubleValue = (double)v3;
            return 404;
        }
        if (v4 == 69 || v4 == 101 || v4 == 68 || v4 == 100 || v4 == 46) {
            final StringBuffer a1 = this.textBuffer;
            a1.setLength(0);
            a1.append(v3);
            return this.readDouble(a1, v4, v2);
        }
        if (v4 == 76 || v4 == 108) {
            return 403;
        }
        this.ungetc(v4);
        return 402;
    }
    
    private int readDouble(final StringBuffer a3, int v1, final Token v2) {
        if (v1 != 69 && v1 != 101 && v1 != 68 && v1 != 100) {
            a3.append((char)v1);
            while (true) {
                v1 = this.getc();
                if (48 > v1 || v1 > 57) {
                    break;
                }
                a3.append((char)v1);
            }
        }
        if (v1 == 69 || v1 == 101) {
            a3.append((char)v1);
            v1 = this.getc();
            if (v1 == 43 || v1 == 45) {
                a3.append((char)v1);
                v1 = this.getc();
            }
            while (48 <= v1 && v1 <= 57) {
                a3.append((char)v1);
                v1 = this.getc();
            }
        }
        try {
            v2.doubleValue = Double.parseDouble(a3.toString());
        }
        catch (NumberFormatException a4) {
            return 500;
        }
        if (v1 == 70 || v1 == 102) {
            return 404;
        }
        if (v1 != 68 && v1 != 100) {
            this.ungetc(v1);
        }
        return 405;
    }
    
    private int readSeparator(final int v-1) {
        int v3;
        if (33 <= v-1 && v-1 <= 63) {
            final int v2 = Lex.equalOps[v-1 - 33];
            if (v2 == 0) {
                return v-1;
            }
            v3 = this.getc();
            if (v-1 == v3) {
                switch (v-1) {
                    case 61: {
                        return 358;
                    }
                    case 43: {
                        return 362;
                    }
                    case 45: {
                        return 363;
                    }
                    case 38: {
                        return 369;
                    }
                    case 60: {
                        final int a1 = this.getc();
                        if (a1 == 61) {
                            return 365;
                        }
                        this.ungetc(a1);
                        return 364;
                    }
                    case 62: {
                        int v4 = this.getc();
                        if (v4 == 61) {
                            return 367;
                        }
                        if (v4 != 62) {
                            this.ungetc(v4);
                            return 366;
                        }
                        v4 = this.getc();
                        if (v4 == 61) {
                            return 371;
                        }
                        this.ungetc(v4);
                        return 370;
                    }
                }
            }
            else if (v3 == 61) {
                return v2;
            }
        }
        else if (v-1 == 94) {
            v3 = this.getc();
            if (v3 == 61) {
                return 360;
            }
        }
        else {
            if (v-1 != 124) {
                return v-1;
            }
            v3 = this.getc();
            if (v3 == 61) {
                return 361;
            }
            if (v3 == 124) {
                return 368;
            }
        }
        this.ungetc(v3);
        return v-1;
    }
    
    private int readIdentifier(int a1, final Token a2) {
        final StringBuffer v1 = this.textBuffer;
        v1.setLength(0);
        do {
            v1.append((char)a1);
            a1 = this.getc();
        } while (Character.isJavaIdentifierPart((char)a1));
        this.ungetc(a1);
        final String v2 = v1.toString();
        final int v3 = Lex.ktable.lookup(v2);
        if (v3 >= 0) {
            return v3;
        }
        a2.textValue = v2;
        return 400;
    }
    
    private static boolean isBlank(final int a1) {
        return a1 == 32 || a1 == 9 || a1 == 12 || a1 == 13 || a1 == 10;
    }
    
    private static boolean isDigit(final int a1) {
        return 48 <= a1 && a1 <= 57;
    }
    
    private void ungetc(final int a1) {
        this.lastChar = a1;
    }
    
    public String getTextAround() {
        int v1 = this.position - 10;
        if (v1 < 0) {
            v1 = 0;
        }
        int v2 = this.position + 10;
        if (v2 > this.maxlen) {
            v2 = this.maxlen;
        }
        return this.input.substring(v1, v2);
    }
    
    private int getc() {
        if (this.lastChar >= 0) {
            final int v1 = this.lastChar;
            this.lastChar = -1;
            return v1;
        }
        if (this.position < this.maxlen) {
            return this.input.charAt(this.position++);
        }
        return -1;
    }
    
    static {
        equalOps = new int[] { 350, 0, 0, 0, 351, 352, 0, 0, 0, 353, 354, 0, 355, 0, 356, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 357, 358, 359, 0 };
        (ktable = new KeywordTable()).append("abstract", 300);
        Lex.ktable.append("boolean", 301);
        Lex.ktable.append("break", 302);
        Lex.ktable.append("byte", 303);
        Lex.ktable.append("case", 304);
        Lex.ktable.append("catch", 305);
        Lex.ktable.append("char", 306);
        Lex.ktable.append("class", 307);
        Lex.ktable.append("const", 308);
        Lex.ktable.append("continue", 309);
        Lex.ktable.append("default", 310);
        Lex.ktable.append("do", 311);
        Lex.ktable.append("double", 312);
        Lex.ktable.append("else", 313);
        Lex.ktable.append("extends", 314);
        Lex.ktable.append("false", 411);
        Lex.ktable.append("final", 315);
        Lex.ktable.append("finally", 316);
        Lex.ktable.append("float", 317);
        Lex.ktable.append("for", 318);
        Lex.ktable.append("goto", 319);
        Lex.ktable.append("if", 320);
        Lex.ktable.append("implements", 321);
        Lex.ktable.append("import", 322);
        Lex.ktable.append("instanceof", 323);
        Lex.ktable.append("int", 324);
        Lex.ktable.append("interface", 325);
        Lex.ktable.append("long", 326);
        Lex.ktable.append("native", 327);
        Lex.ktable.append("new", 328);
        Lex.ktable.append("null", 412);
        Lex.ktable.append("package", 329);
        Lex.ktable.append("private", 330);
        Lex.ktable.append("protected", 331);
        Lex.ktable.append("public", 332);
        Lex.ktable.append("return", 333);
        Lex.ktable.append("short", 334);
        Lex.ktable.append("static", 335);
        Lex.ktable.append("strictfp", 347);
        Lex.ktable.append("super", 336);
        Lex.ktable.append("switch", 337);
        Lex.ktable.append("synchronized", 338);
        Lex.ktable.append("this", 339);
        Lex.ktable.append("throw", 340);
        Lex.ktable.append("throws", 341);
        Lex.ktable.append("transient", 342);
        Lex.ktable.append("true", 410);
        Lex.ktable.append("try", 343);
        Lex.ktable.append("void", 344);
        Lex.ktable.append("volatile", 345);
        Lex.ktable.append("while", 346);
    }
}
