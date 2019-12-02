package com.fasterxml.jackson.core.util;

import java.math.*;
import com.fasterxml.jackson.core.io.*;
import java.io.*;
import java.util.*;

public final class TextBuffer
{
    static final char[] NO_CHARS;
    static final int MIN_SEGMENT_LEN = 1000;
    static final int MAX_SEGMENT_LEN = 262144;
    private final BufferRecycler _allocator;
    private char[] _inputBuffer;
    private int _inputStart;
    private int _inputLen;
    private ArrayList<char[]> _segments;
    private boolean _hasSegments;
    private int _segmentSize;
    private char[] _currentSegment;
    private int _currentSize;
    private String _resultString;
    private char[] _resultArray;
    
    public TextBuffer(final BufferRecycler a1) {
        super();
        this._allocator = a1;
    }
    
    public void releaseBuffers() {
        if (this._allocator == null) {
            this.resetWithEmpty();
        }
        else if (this._currentSegment != null) {
            this.resetWithEmpty();
            final char[] v1 = this._currentSegment;
            this._currentSegment = null;
            this._allocator.releaseCharBuffer(2, v1);
        }
    }
    
    public void resetWithEmpty() {
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
    }
    
    public void resetWith(final char a1) {
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        else if (this._currentSegment == null) {
            this._currentSegment = this.buf(1);
        }
        this._currentSegment[0] = a1;
        final int n = 1;
        this._segmentSize = n;
        this._currentSize = n;
    }
    
    public void resetWithShared(final char[] a1, final int a2, final int a3) {
        this._resultString = null;
        this._resultArray = null;
        this._inputBuffer = a1;
        this._inputStart = a2;
        this._inputLen = a3;
        if (this._hasSegments) {
            this.clearSegments();
        }
    }
    
    public void resetWithCopy(final char[] a1, final int a2, final int a3) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        else if (this._currentSegment == null) {
            this._currentSegment = this.buf(a3);
        }
        final int n = 0;
        this._segmentSize = n;
        this._currentSize = n;
        this.append(a1, a2, a3);
    }
    
    public void resetWithCopy(final String a1, final int a2, final int a3) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        else if (this._currentSegment == null) {
            this._currentSegment = this.buf(a3);
        }
        final int n = 0;
        this._segmentSize = n;
        this._currentSize = n;
        this.append(a1, a2, a3);
    }
    
    public void resetWithString(final String a1) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = a1;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        this._currentSize = 0;
    }
    
    public char[] getBufferWithoutReset() {
        return this._currentSegment;
    }
    
    private char[] buf(final int a1) {
        if (this._allocator != null) {
            return this._allocator.allocCharBuffer(2, a1);
        }
        return new char[Math.max(a1, 1000)];
    }
    
    private void clearSegments() {
        this._hasSegments = false;
        this._segments.clear();
        final int n = 0;
        this._segmentSize = n;
        this._currentSize = n;
    }
    
    public int size() {
        if (this._inputStart >= 0) {
            return this._inputLen;
        }
        if (this._resultArray != null) {
            return this._resultArray.length;
        }
        if (this._resultString != null) {
            return this._resultString.length();
        }
        return this._segmentSize + this._currentSize;
    }
    
    public int getTextOffset() {
        return (this._inputStart >= 0) ? this._inputStart : 0;
    }
    
    public boolean hasTextAsCharacters() {
        return this._inputStart >= 0 || this._resultArray != null || this._resultString == null;
    }
    
    public char[] getTextBuffer() {
        if (this._inputStart >= 0) {
            return this._inputBuffer;
        }
        if (this._resultArray != null) {
            return this._resultArray;
        }
        if (this._resultString != null) {
            return this._resultArray = this._resultString.toCharArray();
        }
        if (!this._hasSegments) {
            return (this._currentSegment == null) ? TextBuffer.NO_CHARS : this._currentSegment;
        }
        return this.contentsAsArray();
    }
    
    public String contentsAsString() {
        if (this._resultString == null) {
            if (this._resultArray != null) {
                this._resultString = new String(this._resultArray);
            }
            else if (this._inputStart >= 0) {
                if (this._inputLen < 1) {
                    return this._resultString = "";
                }
                this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
            }
            else {
                final int segmentSize = this._segmentSize;
                final int currentSize = this._currentSize;
                if (segmentSize == 0) {
                    this._resultString = ((currentSize == 0) ? "" : new String(this._currentSegment, 0, currentSize));
                }
                else {
                    final StringBuilder sb = new StringBuilder(segmentSize + currentSize);
                    if (this._segments != null) {
                        for (int i = 0, v0 = this._segments.size(); i < v0; ++i) {
                            final char[] v2 = this._segments.get(i);
                            sb.append(v2, 0, v2.length);
                        }
                    }
                    sb.append(this._currentSegment, 0, this._currentSize);
                    this._resultString = sb.toString();
                }
            }
        }
        return this._resultString;
    }
    
    public char[] contentsAsArray() {
        char[] v1 = this._resultArray;
        if (v1 == null) {
            v1 = (this._resultArray = this.resultArray());
        }
        return v1;
    }
    
    public BigDecimal contentsAsDecimal() throws NumberFormatException {
        if (this._resultArray != null) {
            return NumberInput.parseBigDecimal(this._resultArray);
        }
        if (this._inputStart >= 0 && this._inputBuffer != null) {
            return NumberInput.parseBigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
        }
        if (this._segmentSize == 0 && this._currentSegment != null) {
            return NumberInput.parseBigDecimal(this._currentSegment, 0, this._currentSize);
        }
        return NumberInput.parseBigDecimal(this.contentsAsArray());
    }
    
    public double contentsAsDouble() throws NumberFormatException {
        return NumberInput.parseDouble(this.contentsAsString());
    }
    
    public int contentsAsInt(final boolean a1) {
        if (this._inputStart >= 0 && this._inputBuffer != null) {
            if (a1) {
                return -NumberInput.parseInt(this._inputBuffer, this._inputStart + 1, this._inputLen - 1);
            }
            return NumberInput.parseInt(this._inputBuffer, this._inputStart, this._inputLen);
        }
        else {
            if (a1) {
                return -NumberInput.parseInt(this._currentSegment, 1, this._currentSize - 1);
            }
            return NumberInput.parseInt(this._currentSegment, 0, this._currentSize);
        }
    }
    
    public long contentsAsLong(final boolean a1) {
        if (this._inputStart >= 0 && this._inputBuffer != null) {
            if (a1) {
                return -NumberInput.parseLong(this._inputBuffer, this._inputStart + 1, this._inputLen - 1);
            }
            return NumberInput.parseLong(this._inputBuffer, this._inputStart, this._inputLen);
        }
        else {
            if (a1) {
                return -NumberInput.parseLong(this._currentSegment, 1, this._currentSize - 1);
            }
            return NumberInput.parseLong(this._currentSegment, 0, this._currentSize);
        }
    }
    
    public int contentsToWriter(final Writer v-3) throws IOException {
        if (this._resultArray != null) {
            v-3.write(this._resultArray);
            return this._resultArray.length;
        }
        if (this._resultString != null) {
            v-3.write(this._resultString);
            return this._resultString.length();
        }
        if (this._inputStart >= 0) {
            final int a1 = this._inputLen;
            if (a1 > 0) {
                v-3.write(this._inputBuffer, this._inputStart, a1);
            }
            return a1;
        }
        int n = 0;
        if (this._segments != null) {
            for (int i = 0, v0 = this._segments.size(); i < v0; ++i) {
                final char[] v2 = this._segments.get(i);
                final int v3 = v2.length;
                v-3.write(v2, 0, v3);
                n += v3;
            }
        }
        int i = this._currentSize;
        if (i > 0) {
            v-3.write(this._currentSegment, 0, i);
            n += i;
        }
        return n;
    }
    
    public void ensureNotShared() {
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
    }
    
    public void append(final char a1) {
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] v1 = this._currentSegment;
        if (this._currentSize >= v1.length) {
            this.expand(1);
            v1 = this._currentSegment;
        }
        v1[this._currentSize++] = a1;
    }
    
    public void append(final char[] a3, int v1, int v2) {
        if (this._inputStart >= 0) {
            this.unshare(v2);
        }
        this._resultString = null;
        this._resultArray = null;
        final char[] v3 = this._currentSegment;
        final int v4 = v3.length - this._currentSize;
        if (v4 >= v2) {
            System.arraycopy(a3, v1, v3, this._currentSize, v2);
            this._currentSize += v2;
            return;
        }
        if (v4 > 0) {
            System.arraycopy(a3, v1, v3, this._currentSize, v4);
            v1 += v4;
            v2 -= v4;
        }
        do {
            this.expand(v2);
            final int a4 = Math.min(this._currentSegment.length, v2);
            System.arraycopy(a3, v1, this._currentSegment, 0, a4);
            this._currentSize += a4;
            v1 += a4;
            v2 -= a4;
        } while (v2 > 0);
    }
    
    public void append(final String a3, int v1, int v2) {
        if (this._inputStart >= 0) {
            this.unshare(v2);
        }
        this._resultString = null;
        this._resultArray = null;
        final char[] v3 = this._currentSegment;
        final int v4 = v3.length - this._currentSize;
        if (v4 >= v2) {
            a3.getChars(v1, v1 + v2, v3, this._currentSize);
            this._currentSize += v2;
            return;
        }
        if (v4 > 0) {
            a3.getChars(v1, v1 + v4, v3, this._currentSize);
            v2 -= v4;
            v1 += v4;
        }
        do {
            this.expand(v2);
            final int a4 = Math.min(this._currentSegment.length, v2);
            a3.getChars(v1, v1 + a4, this._currentSegment, 0);
            this._currentSize += a4;
            v1 += a4;
            v2 -= a4;
        } while (v2 > 0);
    }
    
    public char[] getCurrentSegment() {
        if (this._inputStart >= 0) {
            this.unshare(1);
        }
        else {
            final char[] v1 = this._currentSegment;
            if (v1 == null) {
                this._currentSegment = this.buf(0);
            }
            else if (this._currentSize >= v1.length) {
                this.expand(1);
            }
        }
        return this._currentSegment;
    }
    
    public char[] emptyAndGetCurrentSegment() {
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        char[] v1 = this._currentSegment;
        if (v1 == null) {
            v1 = (this._currentSegment = this.buf(0));
        }
        return v1;
    }
    
    public int getCurrentSegmentSize() {
        return this._currentSize;
    }
    
    public void setCurrentLength(final int a1) {
        this._currentSize = a1;
    }
    
    public String setCurrentAndReturn(final int a1) {
        this._currentSize = a1;
        if (this._segmentSize > 0) {
            return this.contentsAsString();
        }
        final int v1 = this._currentSize;
        final String v2 = (v1 == 0) ? "" : new String(this._currentSegment, 0, v1);
        return this._resultString = v2;
    }
    
    public char[] finishCurrentSegment() {
        if (this._segments == null) {
            this._segments = new ArrayList<char[]>();
        }
        this._hasSegments = true;
        this._segments.add(this._currentSegment);
        final int v1 = this._currentSegment.length;
        this._segmentSize += v1;
        this._currentSize = 0;
        int v2 = v1 + (v1 >> 1);
        if (v2 < 1000) {
            v2 = 1000;
        }
        else if (v2 > 262144) {
            v2 = 262144;
        }
        final char[] v3 = this.carr(v2);
        return this._currentSegment = v3;
    }
    
    public char[] expandCurrentSegment() {
        final char[] v1 = this._currentSegment;
        final int v2 = v1.length;
        int v3 = v2 + (v2 >> 1);
        if (v3 > 262144) {
            v3 = v2 + (v2 >> 2);
        }
        return this._currentSegment = Arrays.copyOf(v1, v3);
    }
    
    public char[] expandCurrentSegment(final int a1) {
        char[] v1 = this._currentSegment;
        if (v1.length >= a1) {
            return v1;
        }
        v1 = (this._currentSegment = Arrays.copyOf(v1, a1));
        return v1;
    }
    
    @Override
    public String toString() {
        return this.contentsAsString();
    }
    
    private void unshare(final int a1) {
        final int v1 = this._inputLen;
        this._inputLen = 0;
        final char[] v2 = this._inputBuffer;
        this._inputBuffer = null;
        final int v3 = this._inputStart;
        this._inputStart = -1;
        final int v4 = v1 + a1;
        if (this._currentSegment == null || v4 > this._currentSegment.length) {
            this._currentSegment = this.buf(v4);
        }
        if (v1 > 0) {
            System.arraycopy(v2, v3, this._currentSegment, 0, v1);
        }
        this._segmentSize = 0;
        this._currentSize = v1;
    }
    
    private void expand(final int a1) {
        if (this._segments == null) {
            this._segments = new ArrayList<char[]>();
        }
        final char[] v1 = this._currentSegment;
        this._hasSegments = true;
        this._segments.add(v1);
        this._segmentSize += v1.length;
        this._currentSize = 0;
        final int v2 = v1.length;
        int v3 = v2 + (v2 >> 1);
        if (v3 < 1000) {
            v3 = 1000;
        }
        else if (v3 > 262144) {
            v3 = 262144;
        }
        this._currentSegment = this.carr(v3);
    }
    
    private char[] resultArray() {
        if (this._resultString != null) {
            return this._resultString.toCharArray();
        }
        if (this._inputStart >= 0) {
            final int v1 = this._inputLen;
            if (v1 < 1) {
                return TextBuffer.NO_CHARS;
            }
            final int v2 = this._inputStart;
            if (v2 == 0) {
                return Arrays.copyOf(this._inputBuffer, v1);
            }
            return Arrays.copyOfRange(this._inputBuffer, v2, v2 + v1);
        }
        else {
            final int v1 = this.size();
            if (v1 < 1) {
                return TextBuffer.NO_CHARS;
            }
            int v2 = 0;
            final char[] v3 = this.carr(v1);
            if (this._segments != null) {
                for (int v4 = 0, v5 = this._segments.size(); v4 < v5; ++v4) {
                    final char[] v6 = this._segments.get(v4);
                    final int v7 = v6.length;
                    System.arraycopy(v6, 0, v3, v2, v7);
                    v2 += v7;
                }
            }
            System.arraycopy(this._currentSegment, 0, v3, v2, this._currentSize);
            return v3;
        }
    }
    
    private char[] carr(final int a1) {
        return new char[a1];
    }
    
    static {
        NO_CHARS = new char[0];
    }
}
