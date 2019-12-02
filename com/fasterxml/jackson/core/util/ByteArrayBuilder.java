package com.fasterxml.jackson.core.util;

import java.io.*;
import java.util.*;

public final class ByteArrayBuilder extends OutputStream
{
    public static final byte[] NO_BYTES;
    private static final int INITIAL_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 262144;
    static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
    private final BufferRecycler _bufferRecycler;
    private final LinkedList<byte[]> _pastBlocks;
    private int _pastLen;
    private byte[] _currBlock;
    private int _currBlockPtr;
    
    public ByteArrayBuilder() {
        this(null);
    }
    
    public ByteArrayBuilder(final BufferRecycler a1) {
        this(a1, 500);
    }
    
    public ByteArrayBuilder(final int a1) {
        this(null, a1);
    }
    
    public ByteArrayBuilder(final BufferRecycler a1, final int a2) {
        super();
        this._pastBlocks = new LinkedList<byte[]>();
        this._bufferRecycler = a1;
        this._currBlock = ((a1 == null) ? new byte[a2] : a1.allocByteBuffer(2));
    }
    
    public void reset() {
        this._pastLen = 0;
        this._currBlockPtr = 0;
        if (!this._pastBlocks.isEmpty()) {
            this._pastBlocks.clear();
        }
    }
    
    public int size() {
        return this._pastLen + this._currBlockPtr;
    }
    
    public void release() {
        this.reset();
        if (this._bufferRecycler != null && this._currBlock != null) {
            this._bufferRecycler.releaseByteBuffer(2, this._currBlock);
            this._currBlock = null;
        }
    }
    
    public void append(final int a1) {
        if (this._currBlockPtr >= this._currBlock.length) {
            this._allocMore();
        }
        this._currBlock[this._currBlockPtr++] = (byte)a1;
    }
    
    public void appendTwoBytes(final int a1) {
        if (this._currBlockPtr + 1 < this._currBlock.length) {
            this._currBlock[this._currBlockPtr++] = (byte)(a1 >> 8);
            this._currBlock[this._currBlockPtr++] = (byte)a1;
        }
        else {
            this.append(a1 >> 8);
            this.append(a1);
        }
    }
    
    public void appendThreeBytes(final int a1) {
        if (this._currBlockPtr + 2 < this._currBlock.length) {
            this._currBlock[this._currBlockPtr++] = (byte)(a1 >> 16);
            this._currBlock[this._currBlockPtr++] = (byte)(a1 >> 8);
            this._currBlock[this._currBlockPtr++] = (byte)a1;
        }
        else {
            this.append(a1 >> 16);
            this.append(a1 >> 8);
            this.append(a1);
        }
    }
    
    public void appendFourBytes(final int a1) {
        if (this._currBlockPtr + 3 < this._currBlock.length) {
            this._currBlock[this._currBlockPtr++] = (byte)(a1 >> 24);
            this._currBlock[this._currBlockPtr++] = (byte)(a1 >> 16);
            this._currBlock[this._currBlockPtr++] = (byte)(a1 >> 8);
            this._currBlock[this._currBlockPtr++] = (byte)a1;
        }
        else {
            this.append(a1 >> 24);
            this.append(a1 >> 16);
            this.append(a1 >> 8);
            this.append(a1);
        }
    }
    
    public byte[] toByteArray() {
        final int n = this._pastLen + this._currBlockPtr;
        if (n == 0) {
            return ByteArrayBuilder.NO_BYTES;
        }
        final byte[] array = new byte[n];
        int n2 = 0;
        for (final byte[] v0 : this._pastBlocks) {
            final int v2 = v0.length;
            System.arraycopy(v0, 0, array, n2, v2);
            n2 += v2;
        }
        System.arraycopy(this._currBlock, 0, array, n2, this._currBlockPtr);
        n2 += this._currBlockPtr;
        if (n2 != n) {
            throw new RuntimeException("Internal error: total len assumed to be " + n + ", copied " + n2 + " bytes");
        }
        if (!this._pastBlocks.isEmpty()) {
            this.reset();
        }
        return array;
    }
    
    public byte[] resetAndGetFirstSegment() {
        this.reset();
        return this._currBlock;
    }
    
    public byte[] finishCurrentSegment() {
        this._allocMore();
        return this._currBlock;
    }
    
    public byte[] completeAndCoalesce(final int a1) {
        this._currBlockPtr = a1;
        return this.toByteArray();
    }
    
    public byte[] getCurrentSegment() {
        return this._currBlock;
    }
    
    public void setCurrentSegmentLength(final int a1) {
        this._currBlockPtr = a1;
    }
    
    public int getCurrentSegmentLength() {
        return this._currBlockPtr;
    }
    
    @Override
    public void write(final byte[] a1) {
        this.write(a1, 0, a1.length);
    }
    
    @Override
    public void write(final byte[] v1, int v2, int v3) {
        while (true) {
            final int a1 = this._currBlock.length - this._currBlockPtr;
            final int a2 = Math.min(a1, v3);
            if (a2 > 0) {
                System.arraycopy(v1, v2, this._currBlock, this._currBlockPtr, a2);
                v2 += a2;
                this._currBlockPtr += a2;
                v3 -= a2;
            }
            if (v3 <= 0) {
                break;
            }
            this._allocMore();
        }
    }
    
    @Override
    public void write(final int a1) {
        this.append(a1);
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public void flush() {
    }
    
    private void _allocMore() {
        final int v1 = this._pastLen + this._currBlock.length;
        if (v1 < 0) {
            throw new IllegalStateException("Maximum Java array size (2GB) exceeded by `ByteArrayBuilder`");
        }
        this._pastLen = v1;
        int v2 = Math.max(this._pastLen >> 1, 1000);
        if (v2 > 262144) {
            v2 = 262144;
        }
        this._pastBlocks.add(this._currBlock);
        this._currBlock = new byte[v2];
        this._currBlockPtr = 0;
    }
    
    static {
        NO_BYTES = new byte[0];
    }
}
