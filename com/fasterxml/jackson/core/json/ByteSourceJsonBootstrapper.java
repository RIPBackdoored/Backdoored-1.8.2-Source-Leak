package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.sym.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.format.*;
import java.io.*;

public final class ByteSourceJsonBootstrapper
{
    public static final byte UTF8_BOM_1 = -17;
    public static final byte UTF8_BOM_2 = -69;
    public static final byte UTF8_BOM_3 = -65;
    private final IOContext _context;
    private final InputStream _in;
    private final byte[] _inputBuffer;
    private int _inputPtr;
    private int _inputEnd;
    private final boolean _bufferRecyclable;
    private boolean _bigEndian;
    private int _bytesPerChar;
    
    public ByteSourceJsonBootstrapper(final IOContext a1, final InputStream a2) {
        super();
        this._bigEndian = true;
        this._context = a1;
        this._in = a2;
        this._inputBuffer = a1.allocReadIOBuffer();
        final int n = 0;
        this._inputPtr = n;
        this._inputEnd = n;
        this._bufferRecyclable = true;
    }
    
    public ByteSourceJsonBootstrapper(final IOContext a1, final byte[] a2, final int a3, final int a4) {
        super();
        this._bigEndian = true;
        this._context = a1;
        this._in = null;
        this._inputBuffer = a2;
        this._inputPtr = a3;
        this._inputEnd = a3 + a4;
        this._bufferRecyclable = false;
    }
    
    public JsonEncoding detectEncoding() throws IOException {
        boolean v0 = false;
        if (this.ensureLoaded(4)) {
            final int v2 = this._inputBuffer[this._inputPtr] << 24 | (this._inputBuffer[this._inputPtr + 1] & 0xFF) << 16 | (this._inputBuffer[this._inputPtr + 2] & 0xFF) << 8 | (this._inputBuffer[this._inputPtr + 3] & 0xFF);
            if (this.handleBOM(v2)) {
                v0 = true;
            }
            else if (this.checkUTF32(v2)) {
                v0 = true;
            }
            else if (this.checkUTF16(v2 >>> 16)) {
                v0 = true;
            }
        }
        else if (this.ensureLoaded(2)) {
            final int v2 = (this._inputBuffer[this._inputPtr] & 0xFF) << 8 | (this._inputBuffer[this._inputPtr + 1] & 0xFF);
            if (this.checkUTF16(v2)) {
                v0 = true;
            }
        }
        JsonEncoding v3 = null;
        if (!v0) {
            v3 = JsonEncoding.UTF8;
        }
        else {
            switch (this._bytesPerChar) {
                case 1: {
                    v3 = JsonEncoding.UTF8;
                    break;
                }
                case 2: {
                    v3 = (this._bigEndian ? JsonEncoding.UTF16_BE : JsonEncoding.UTF16_LE);
                    break;
                }
                case 4: {
                    v3 = (this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE);
                    break;
                }
                default: {
                    throw new RuntimeException("Internal error");
                }
            }
        }
        this._context.setEncoding(v3);
        return v3;
    }
    
    public static int skipUTF8BOM(final DataInput a1) throws IOException {
        int v1 = a1.readUnsignedByte();
        if (v1 != 239) {
            return v1;
        }
        v1 = a1.readUnsignedByte();
        if (v1 != 187) {
            throw new IOException("Unexpected byte 0x" + Integer.toHexString(v1) + " following 0xEF; should get 0xBB as part of UTF-8 BOM");
        }
        v1 = a1.readUnsignedByte();
        if (v1 != 191) {
            throw new IOException("Unexpected byte 0x" + Integer.toHexString(v1) + " following 0xEF 0xBB; should get 0xBF as part of UTF-8 BOM");
        }
        return a1.readUnsignedByte();
    }
    
    public Reader constructReader() throws IOException {
        final JsonEncoding v0 = this._context.getEncoding();
        switch (v0.bits()) {
            case 8:
            case 16: {
                InputStream v2 = this._in;
                if (v2 == null) {
                    v2 = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
                }
                else if (this._inputPtr < this._inputEnd) {
                    v2 = new MergedStream(this._context, v2, this._inputBuffer, this._inputPtr, this._inputEnd);
                }
                return new InputStreamReader(v2, v0.getJavaName());
            }
            case 32: {
                return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
            }
            default: {
                throw new RuntimeException("Internal error");
            }
        }
    }
    
    public JsonParser constructParser(final int a3, final ObjectCodec a4, final ByteQuadsCanonicalizer a5, final CharsToNameCanonicalizer v1, final int v2) throws IOException {
        final JsonEncoding v3 = this.detectEncoding();
        if (v3 == JsonEncoding.UTF8 && JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(v2)) {
            final ByteQuadsCanonicalizer a6 = a5.makeChild(v2);
            return new UTF8StreamJsonParser(this._context, a3, this._in, a4, a6, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
        }
        return new ReaderBasedJsonParser(this._context, a3, this.constructReader(), a4, v1.makeChild(v2));
    }
    
    public static MatchStrength hasJSONFormat(final InputAccessor a1) throws IOException {
        if (!a1.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
        }
        byte v1 = a1.nextByte();
        if (v1 == -17) {
            if (!a1.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (a1.nextByte() != -69) {
                return MatchStrength.NO_MATCH;
            }
            if (!a1.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (a1.nextByte() != -65) {
                return MatchStrength.NO_MATCH;
            }
            if (!a1.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            v1 = a1.nextByte();
        }
        int v2 = skipSpace(a1, v1);
        if (v2 < 0) {
            return MatchStrength.INCONCLUSIVE;
        }
        if (v2 == 123) {
            v2 = skipSpace(a1);
            if (v2 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (v2 == 34 || v2 == 125) {
                return MatchStrength.SOLID_MATCH;
            }
            return MatchStrength.NO_MATCH;
        }
        else if (v2 == 91) {
            v2 = skipSpace(a1);
            if (v2 < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (v2 == 93 || v2 == 91) {
                return MatchStrength.SOLID_MATCH;
            }
            return MatchStrength.SOLID_MATCH;
        }
        else {
            final MatchStrength v3 = MatchStrength.WEAK_MATCH;
            if (v2 == 34) {
                return v3;
            }
            if (v2 <= 57 && v2 >= 48) {
                return v3;
            }
            if (v2 == 45) {
                v2 = skipSpace(a1);
                if (v2 < 0) {
                    return MatchStrength.INCONCLUSIVE;
                }
                return (v2 <= 57 && v2 >= 48) ? v3 : MatchStrength.NO_MATCH;
            }
            else {
                if (v2 == 110) {
                    return tryMatch(a1, "ull", v3);
                }
                if (v2 == 116) {
                    return tryMatch(a1, "rue", v3);
                }
                if (v2 == 102) {
                    return tryMatch(a1, "alse", v3);
                }
                return MatchStrength.NO_MATCH;
            }
        }
    }
    
    private static MatchStrength tryMatch(final InputAccessor a3, final String v1, final MatchStrength v2) throws IOException {
        for (int a4 = 0, a5 = v1.length(); a4 < a5; ++a4) {
            if (!a3.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (a3.nextByte() != v1.charAt(a4)) {
                return MatchStrength.NO_MATCH;
            }
        }
        return v2;
    }
    
    private static int skipSpace(final InputAccessor a1) throws IOException {
        if (!a1.hasMoreBytes()) {
            return -1;
        }
        return skipSpace(a1, a1.nextByte());
    }
    
    private static int skipSpace(final InputAccessor a2, byte v1) throws IOException {
        while (true) {
            final int a3 = v1 & 0xFF;
            if (a3 != 32 && a3 != 13 && a3 != 10 && a3 != 9) {
                return a3;
            }
            if (!a2.hasMoreBytes()) {
                return -1;
            }
            v1 = a2.nextByte();
        }
    }
    
    private boolean handleBOM(final int a1) throws IOException {
        switch (a1) {
            case 65279: {
                this._bigEndian = true;
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                return true;
            }
            case -131072: {
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                this._bigEndian = false;
                return true;
            }
            case 65534: {
                this.reportWeirdUCS4("2143");
                break;
            }
            case -16842752: {
                this.reportWeirdUCS4("3412");
                break;
            }
        }
        final int v1 = a1 >>> 16;
        if (v1 == 65279) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            return this._bigEndian = true;
        }
        if (v1 == 65534) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            this._bigEndian = false;
            return true;
        }
        if (a1 >>> 8 == 15711167) {
            this._inputPtr += 3;
            this._bytesPerChar = 1;
            return this._bigEndian = true;
        }
        return false;
    }
    
    private boolean checkUTF32(final int a1) throws IOException {
        if (a1 >> 8 == 0) {
            this._bigEndian = true;
        }
        else if ((a1 & 0xFFFFFF) == 0x0) {
            this._bigEndian = false;
        }
        else if ((a1 & 0xFF00FFFF) == 0x0) {
            this.reportWeirdUCS4("3412");
        }
        else {
            if ((a1 & 0xFFFF00FF) != 0x0) {
                return false;
            }
            this.reportWeirdUCS4("2143");
        }
        this._bytesPerChar = 4;
        return true;
    }
    
    private boolean checkUTF16(final int a1) {
        if ((a1 & 0xFF00) == 0x0) {
            this._bigEndian = true;
        }
        else {
            if ((a1 & 0xFF) != 0x0) {
                return false;
            }
            this._bigEndian = false;
        }
        this._bytesPerChar = 2;
        return true;
    }
    
    private void reportWeirdUCS4(final String a1) throws IOException {
        throw new CharConversionException("Unsupported UCS-4 endianness (" + a1 + ") detected");
    }
    
    protected boolean ensureLoaded(final int v-1) throws IOException {
        int v2 = 0;
        for (int v0 = this._inputEnd - this._inputPtr; v0 < v-1; v0 += v2) {
            if (this._in == null) {
                final int a1 = -1;
            }
            else {
                v2 = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
            }
            if (v2 < 1) {
                return false;
            }
            this._inputEnd += v2;
        }
        return true;
    }
}
