package com.sun.jna;

import java.nio.*;

private static class Opaque extends Pointer
{
    private final String MSG;
    
    private Opaque(final long a1) {
        super(a1);
        this.MSG = "This pointer is opaque: " + this;
    }
    
    @Override
    public Pointer share(final long a1, final long a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void clear(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public long indexOf(final long a1, final byte a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final byte[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final char[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final short[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final int[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final long[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final float[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final double[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void read(final long a1, final Pointer[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final byte[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final char[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final short[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final int[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final long[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final float[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final double[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void write(final long a1, final Pointer[] a2, final int a3, final int a4) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public ByteBuffer getByteBuffer(final long a1, final long a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public byte getByte(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public char getChar(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public short getShort(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public int getInt(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public long getLong(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public float getFloat(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public double getDouble(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public Pointer getPointer(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public String getString(final long a1, final String a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public String getWideString(final long a1) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setByte(final long a1, final byte a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setChar(final long a1, final char a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setShort(final long a1, final short a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setInt(final long a1, final int a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setLong(final long a1, final long a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setFloat(final long a1, final float a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setDouble(final long a1, final double a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setPointer(final long a1, final Pointer a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setString(final long a1, final String a2, final String a3) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setWideString(final long a1, final String a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public void setMemory(final long a1, final long a2, final byte a3) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public String dump(final long a1, final int a2) {
        throw new UnsupportedOperationException(this.MSG);
    }
    
    @Override
    public String toString() {
        return "const@0x" + Long.toHexString(this.peer);
    }
    
    Opaque(final long a1, final Pointer$1 a2) {
        this(a1);
    }
}
