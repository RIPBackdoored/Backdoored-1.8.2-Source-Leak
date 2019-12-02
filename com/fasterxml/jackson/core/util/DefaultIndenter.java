package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.*;
import java.io.*;

public class DefaultIndenter extends DefaultPrettyPrinter.NopIndenter
{
    private static final long serialVersionUID = 1L;
    public static final String SYS_LF;
    public static final DefaultIndenter SYSTEM_LINEFEED_INSTANCE;
    private static final int INDENT_LEVELS = 16;
    private final char[] indents;
    private final int charsPerLevel;
    private final String eol;
    
    public DefaultIndenter() {
        this("  ", DefaultIndenter.SYS_LF);
    }
    
    public DefaultIndenter(final String v1, final String v2) {
        super();
        this.charsPerLevel = v1.length();
        this.indents = new char[v1.length() * 16];
        int v3 = 0;
        for (int a1 = 0; a1 < 16; ++a1) {
            v1.getChars(0, v1.length(), this.indents, v3);
            v3 += v1.length();
        }
        this.eol = v2;
    }
    
    public DefaultIndenter withLinefeed(final String a1) {
        if (a1.equals(this.eol)) {
            return this;
        }
        return new DefaultIndenter(this.getIndent(), a1);
    }
    
    public DefaultIndenter withIndent(final String a1) {
        if (a1.equals(this.getIndent())) {
            return this;
        }
        return new DefaultIndenter(a1, this.eol);
    }
    
    @Override
    public boolean isInline() {
        return false;
    }
    
    @Override
    public void writeIndentation(final JsonGenerator a1, int a2) throws IOException {
        a1.writeRaw(this.eol);
        if (a2 > 0) {
            for (a2 *= this.charsPerLevel; a2 > this.indents.length; a2 -= this.indents.length) {
                a1.writeRaw(this.indents, 0, this.indents.length);
            }
            a1.writeRaw(this.indents, 0, a2);
        }
    }
    
    public String getEol() {
        return this.eol;
    }
    
    public String getIndent() {
        return new String(this.indents, 0, this.charsPerLevel);
    }
    
    static {
        String v0;
        try {
            v0 = System.getProperty("line.separator");
        }
        catch (Throwable v2) {
            v0 = "\n";
        }
        SYS_LF = v0;
        SYSTEM_LINEFEED_INSTANCE = new DefaultIndenter("  ", DefaultIndenter.SYS_LF);
    }
}
