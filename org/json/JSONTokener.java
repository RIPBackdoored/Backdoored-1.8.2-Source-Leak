package org.json;

import java.io.*;

public class JSONTokener
{
    private long character;
    private boolean eof;
    private long index;
    private long line;
    private char previous;
    private final Reader reader;
    private boolean usePrevious;
    private long characterPreviousLine;
    
    public JSONTokener(final Reader a1) {
        super();
        this.reader = (a1.markSupported() ? a1 : new BufferedReader(a1));
        this.eof = false;
        this.usePrevious = false;
        this.previous = '\0';
        this.index = 0L;
        this.character = 1L;
        this.characterPreviousLine = 0L;
        this.line = 1L;
    }
    
    public JSONTokener(final InputStream a1) {
        this(new InputStreamReader(a1));
    }
    
    public JSONTokener(final String a1) {
        this(new StringReader(a1));
    }
    
    public void back() throws JSONException {
        if (this.usePrevious || this.index <= 0L) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.decrementIndexes();
        this.usePrevious = true;
        this.eof = false;
    }
    
    private void decrementIndexes() {
        --this.index;
        if (this.previous == '\r' || this.previous == '\n') {
            --this.line;
            this.character = this.characterPreviousLine;
        }
        else if (this.character > 0L) {
            --this.character;
        }
    }
    
    public static int dehexchar(final char a1) {
        if (a1 >= '0' && a1 <= '9') {
            return a1 - '0';
        }
        if (a1 >= 'A' && a1 <= 'F') {
            return a1 - '7';
        }
        if (a1 >= 'a' && a1 <= 'f') {
            return a1 - 'W';
        }
        return -1;
    }
    
    public boolean end() {
        return this.eof && !this.usePrevious;
    }
    
    public boolean more() throws JSONException {
        if (this.usePrevious) {
            return true;
        }
        try {
            this.reader.mark(1);
        }
        catch (IOException v1) {
            throw new JSONException("Unable to preserve stream position", v1);
        }
        try {
            if (this.reader.read() <= 0) {
                this.eof = true;
                return false;
            }
            this.reader.reset();
        }
        catch (IOException v1) {
            throw new JSONException("Unable to read the next character from the stream", v1);
        }
        return true;
    }
    
    public char next() throws JSONException {
        int v1;
        if (this.usePrevious) {
            this.usePrevious = false;
            v1 = this.previous;
        }
        else {
            try {
                v1 = this.reader.read();
            }
            catch (IOException v2) {
                throw new JSONException(v2);
            }
        }
        if (v1 <= 0) {
            this.eof = true;
            return '\0';
        }
        this.incrementIndexes(v1);
        return this.previous = (char)v1;
    }
    
    private void incrementIndexes(final int a1) {
        if (a1 > 0) {
            ++this.index;
            if (a1 == 13) {
                ++this.line;
                this.characterPreviousLine = this.character;
                this.character = 0L;
            }
            else if (a1 == 10) {
                if (this.previous != '\r') {
                    ++this.line;
                    this.characterPreviousLine = this.character;
                }
                this.character = 0L;
            }
            else {
                ++this.character;
            }
        }
    }
    
    public char next(final char a1) throws JSONException {
        final char v1 = this.next();
        if (v1 == a1) {
            return v1;
        }
        if (v1 > '\0') {
            throw this.syntaxError("Expected '" + a1 + "' and instead saw '" + v1 + "'");
        }
        throw this.syntaxError("Expected '" + a1 + "' and instead saw ''");
    }
    
    public String next(final int a1) throws JSONException {
        if (a1 == 0) {
            return "";
        }
        final char[] v1 = new char[a1];
        for (int v2 = 0; v2 < a1; ++v2) {
            v1[v2] = this.next();
            if (this.end()) {
                throw this.syntaxError("Substring bounds error");
            }
        }
        return new String(v1);
    }
    
    public char nextClean() throws JSONException {
        char v1;
        do {
            v1 = this.next();
        } while (v1 != '\0' && v1 <= ' ');
        return v1;
    }
    
    public String nextString(final char v2) throws JSONException {
        final StringBuilder v3 = new StringBuilder();
        while (true) {
            char v4 = this.next();
            switch (v4) {
                case '\0':
                case '\n':
                case '\r': {
                    throw this.syntaxError("Unterminated string");
                }
                case '\\': {
                    v4 = this.next();
                    switch (v4) {
                        case 'b': {
                            v3.append('\b');
                            continue;
                        }
                        case 't': {
                            v3.append('\t');
                            continue;
                        }
                        case 'n': {
                            v3.append('\n');
                            continue;
                        }
                        case 'f': {
                            v3.append('\f');
                            continue;
                        }
                        case 'r': {
                            v3.append('\r');
                            continue;
                        }
                        case 'u': {
                            try {
                                v3.append((char)Integer.parseInt(this.next(4), 16));
                                continue;
                            }
                            catch (NumberFormatException a1) {
                                throw this.syntaxError("Illegal escape.", a1);
                            }
                        }
                        case '\"':
                        case '\'':
                        case '/':
                        case '\\': {
                            v3.append(v4);
                            continue;
                        }
                        default: {
                            throw this.syntaxError("Illegal escape.");
                        }
                    }
                    break;
                }
                default: {
                    if (v4 == v2) {
                        return v3.toString();
                    }
                    v3.append(v4);
                    continue;
                }
            }
        }
    }
    
    public String nextTo(final char v2) throws JSONException {
        final StringBuilder v3 = new StringBuilder();
        char a1;
        while (true) {
            a1 = this.next();
            if (a1 == v2 || a1 == '\0' || a1 == '\n' || a1 == '\r') {
                break;
            }
            v3.append(a1);
        }
        if (a1 != '\0') {
            this.back();
        }
        return v3.toString().trim();
    }
    
    public String nextTo(final String a1) throws JSONException {
        final StringBuilder v2 = new StringBuilder();
        char v3;
        while (true) {
            v3 = this.next();
            if (a1.indexOf(v3) >= 0 || v3 == '\0' || v3 == '\n' || v3 == '\r') {
                break;
            }
            v2.append(v3);
        }
        if (v3 != '\0') {
            this.back();
        }
        return v2.toString().trim();
    }
    
    public Object nextValue() throws JSONException {
        char v1 = this.nextClean();
        switch (v1) {
            case '\"':
            case '\'': {
                return this.nextString(v1);
            }
            case '{': {
                this.back();
                return new JSONObject(this);
            }
            case '[': {
                this.back();
                return new JSONArray(this);
            }
            default: {
                final StringBuilder v2 = new StringBuilder();
                while (v1 >= ' ' && ",:]}/\\\"[{;=#".indexOf(v1) < 0) {
                    v2.append(v1);
                    v1 = this.next();
                }
                if (!this.eof) {
                    this.back();
                }
                final String v3 = v2.toString().trim();
                if ("".equals(v3)) {
                    throw this.syntaxError("Missing value");
                }
                return JSONObject.stringToValue(v3);
            }
        }
    }
    
    public char skipTo(final char v-3) throws JSONException {
        char next;
        try {
            final long a1 = this.index;
            final long v1 = this.character;
            final long v2 = this.line;
            this.reader.mark(1000000);
            do {
                next = this.next();
                if (next == '\0') {
                    this.reader.reset();
                    this.index = a1;
                    this.character = v1;
                    this.line = v2;
                    return '\0';
                }
            } while (next != v-3);
            this.reader.mark(1);
        }
        catch (IOException a2) {
            throw new JSONException(a2);
        }
        this.back();
        return next;
    }
    
    public JSONException syntaxError(final String a1) {
        return new JSONException(a1 + this.toString());
    }
    
    public JSONException syntaxError(final String a1, final Throwable a2) {
        return new JSONException(a1 + this.toString(), a2);
    }
    
    @Override
    public String toString() {
        return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
    }
}
