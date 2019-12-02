package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.*;
import java.util.*;
import java.io.*;

public class DataFormatDetector
{
    public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
    protected final JsonFactory[] _detectors;
    protected final MatchStrength _optimalMatch;
    protected final MatchStrength _minimalMatch;
    protected final int _maxInputLookahead;
    
    public DataFormatDetector(final JsonFactory... a1) {
        this(a1, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
    }
    
    public DataFormatDetector(final Collection<JsonFactory> a1) {
        this((JsonFactory[])a1.toArray(new JsonFactory[a1.size()]));
    }
    
    public DataFormatDetector withOptimalMatch(final MatchStrength a1) {
        if (a1 == this._optimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, a1, this._minimalMatch, this._maxInputLookahead);
    }
    
    public DataFormatDetector withMinimalMatch(final MatchStrength a1) {
        if (a1 == this._minimalMatch) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, a1, this._maxInputLookahead);
    }
    
    public DataFormatDetector withMaxInputLookahead(final int a1) {
        if (a1 == this._maxInputLookahead) {
            return this;
        }
        return new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, a1);
    }
    
    private DataFormatDetector(final JsonFactory[] a1, final MatchStrength a2, final MatchStrength a3, final int a4) {
        super();
        this._detectors = a1;
        this._optimalMatch = a2;
        this._minimalMatch = a3;
        this._maxInputLookahead = a4;
    }
    
    public DataFormatMatcher findFormat(final InputStream a1) throws IOException {
        return this._findFormat(new InputAccessor.Std(a1, new byte[this._maxInputLookahead]));
    }
    
    public DataFormatMatcher findFormat(final byte[] a1) throws IOException {
        return this._findFormat(new InputAccessor.Std(a1));
    }
    
    public DataFormatMatcher findFormat(final byte[] a1, final int a2, final int a3) throws IOException {
        return this._findFormat(new InputAccessor.Std(a1, a2, a3));
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        final int v0 = this._detectors.length;
        if (v0 > 0) {
            sb.append(this._detectors[0].getFormatName());
            for (int v2 = 1; v2 < v0; ++v2) {
                sb.append(", ");
                sb.append(this._detectors[v2].getFormatName());
            }
        }
        sb.append(']');
        return sb.toString();
    }
    
    private DataFormatMatcher _findFormat(final InputAccessor.Std v-5) throws IOException {
        JsonFactory a2 = null;
        MatchStrength a3 = null;
        for (final JsonFactory v2 : this._detectors) {
            v-5.reset();
            final MatchStrength a1 = v2.hasFormat(v-5);
            if (a1 != null) {
                if (a1.ordinal() >= this._minimalMatch.ordinal()) {
                    if (a2 == null || a3.ordinal() < a1.ordinal()) {
                        a2 = v2;
                        a3 = a1;
                        if (a1.ordinal() >= this._optimalMatch.ordinal()) {
                            break;
                        }
                    }
                }
            }
        }
        return v-5.createMatcher(a2, a3);
    }
}
