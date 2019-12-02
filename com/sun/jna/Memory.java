package com.sun.jna;

import java.lang.ref.*;
import java.nio.*;
import java.util.*;

public class Memory extends Pointer
{
    private static final Map<Memory, Reference<Memory>> allocatedMemory;
    private static final WeakMemoryHolder buffers;
    protected long size;
    
    public static void purge() {
        Memory.buffers.clean();
    }
    
    public static void disposeAll() {
        final Collection<Memory> collection = new LinkedList<Memory>(Memory.allocatedMemory.keySet());
        for (final Memory v1 : collection) {
            v1.dispose();
        }
    }
    
    public Memory(final long a1) {
        super();
        this.size = a1;
        if (a1 <= 0L) {
            throw new IllegalArgumentException("Allocation size must be greater than zero");
        }
        this.peer = malloc(a1);
        if (this.peer == 0L) {
            throw new OutOfMemoryError("Cannot allocate " + a1 + " bytes");
        }
        Memory.allocatedMemory.put(this, new WeakReference<Memory>(this));
    }
    
    protected Memory() {
        super();
    }
    
    @Override
    public Pointer share(final long a1) {
        return this.share(a1, this.size() - a1);
    }
    
    @Override
    public Pointer share(final long a1, final long a2) {
        this.boundsCheck(a1, a2);
        return new SharedMemory(a1, a2);
    }
    
    public Memory align(final int v-5) {
        if (v-5 <= 0) {
            throw new IllegalArgumentException("Byte boundary must be positive: " + v-5);
        }
        int i = 0;
        while (i < 32) {
            if (v-5 == 1 << i) {
                final long n = ~(v-5 - 1L);
                if ((this.peer & n) == this.peer) {
                    return this;
                }
                final long a1 = this.peer + v-5 - 1L & n;
                final long v1 = this.peer + this.size - a1;
                if (v1 <= 0L) {
                    throw new IllegalArgumentException("Insufficient memory to align to the requested boundary");
                }
                return (Memory)this.share(a1 - this.peer, v1);
            }
            else {
                ++i;
            }
        }
        throw new IllegalArgumentException("Byte boundary must be a power of two");
    }
    
    @Override
    protected void finalize() {
        this.dispose();
    }
    
    protected synchronized void dispose() {
        try {
            free(this.peer);
        }
        finally {
            Memory.allocatedMemory.remove(this);
            this.peer = 0L;
        }
    }
    
    public void clear() {
        this.clear(this.size);
    }
    
    public boolean valid() {
        return this.peer != 0L;
    }
    
    public long size() {
        return this.size;
    }
    
    protected void boundsCheck(final long v1, final long v3) {
        if (v1 < 0L) {
            throw new IndexOutOfBoundsException("Invalid offset: " + v1);
        }
        if (v1 + v3 > this.size) {
            final String a1 = "Bounds exceeds available space : size=" + this.size + ", offset=" + (v1 + v3);
            throw new IndexOutOfBoundsException(a1);
        }
    }
    
    @Override
    public void read(final long a1, final byte[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 1L);
        super.read(a1, a2, a3, a4);
    }
    
    @Override
    public void read(final long a1, final short[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 2L);
        super.read(a1, a2, a3, a4);
    }
    
    @Override
    public void read(final long a1, final char[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 2L);
        super.read(a1, a2, a3, a4);
    }
    
    @Override
    public void read(final long a1, final int[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 4L);
        super.read(a1, a2, a3, a4);
    }
    
    @Override
    public void read(final long a1, final long[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 8L);
        super.read(a1, a2, a3, a4);
    }
    
    @Override
    public void read(final long a1, final float[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 4L);
        super.read(a1, a2, a3, a4);
    }
    
    @Override
    public void read(final long a1, final double[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 8L);
        super.read(a1, a2, a3, a4);
    }
    
    @Override
    public void write(final long a1, final byte[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 1L);
        super.write(a1, a2, a3, a4);
    }
    
    @Override
    public void write(final long a1, final short[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 2L);
        super.write(a1, a2, a3, a4);
    }
    
    @Override
    public void write(final long a1, final char[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 2L);
        super.write(a1, a2, a3, a4);
    }
    
    @Override
    public void write(final long a1, final int[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 4L);
        super.write(a1, a2, a3, a4);
    }
    
    @Override
    public void write(final long a1, final long[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 8L);
        super.write(a1, a2, a3, a4);
    }
    
    @Override
    public void write(final long a1, final float[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 4L);
        super.write(a1, a2, a3, a4);
    }
    
    @Override
    public void write(final long a1, final double[] a2, final int a3, final int a4) {
        this.boundsCheck(a1, a4 * 8L);
        super.write(a1, a2, a3, a4);
    }
    
    @Override
    public byte getByte(final long a1) {
        this.boundsCheck(a1, 1L);
        return super.getByte(a1);
    }
    
    @Override
    public char getChar(final long a1) {
        this.boundsCheck(a1, 1L);
        return super.getChar(a1);
    }
    
    @Override
    public short getShort(final long a1) {
        this.boundsCheck(a1, 2L);
        return super.getShort(a1);
    }
    
    @Override
    public int getInt(final long a1) {
        this.boundsCheck(a1, 4L);
        return super.getInt(a1);
    }
    
    @Override
    public long getLong(final long a1) {
        this.boundsCheck(a1, 8L);
        return super.getLong(a1);
    }
    
    @Override
    public float getFloat(final long a1) {
        this.boundsCheck(a1, 4L);
        return super.getFloat(a1);
    }
    
    @Override
    public double getDouble(final long a1) {
        this.boundsCheck(a1, 8L);
        return super.getDouble(a1);
    }
    
    @Override
    public Pointer getPointer(final long a1) {
        this.boundsCheck(a1, Pointer.SIZE);
        return super.getPointer(a1);
    }
    
    @Override
    public ByteBuffer getByteBuffer(final long a1, final long a2) {
        this.boundsCheck(a1, a2);
        final ByteBuffer v1 = super.getByteBuffer(a1, a2);
        Memory.buffers.put(v1, this);
        return v1;
    }
    
    @Override
    public String getString(final long a1, final String a2) {
        this.boundsCheck(a1, 0L);
        return super.getString(a1, a2);
    }
    
    @Override
    public String getWideString(final long a1) {
        this.boundsCheck(a1, 0L);
        return super.getWideString(a1);
    }
    
    @Override
    public void setByte(final long a1, final byte a2) {
        this.boundsCheck(a1, 1L);
        super.setByte(a1, a2);
    }
    
    @Override
    public void setChar(final long a1, final char a2) {
        this.boundsCheck(a1, Native.WCHAR_SIZE);
        super.setChar(a1, a2);
    }
    
    @Override
    public void setShort(final long a1, final short a2) {
        this.boundsCheck(a1, 2L);
        super.setShort(a1, a2);
    }
    
    @Override
    public void setInt(final long a1, final int a2) {
        this.boundsCheck(a1, 4L);
        super.setInt(a1, a2);
    }
    
    @Override
    public void setLong(final long a1, final long a2) {
        this.boundsCheck(a1, 8L);
        super.setLong(a1, a2);
    }
    
    @Override
    public void setFloat(final long a1, final float a2) {
        this.boundsCheck(a1, 4L);
        super.setFloat(a1, a2);
    }
    
    @Override
    public void setDouble(final long a1, final double a2) {
        this.boundsCheck(a1, 8L);
        super.setDouble(a1, a2);
    }
    
    @Override
    public void setPointer(final long a1, final Pointer a2) {
        this.boundsCheck(a1, Pointer.SIZE);
        super.setPointer(a1, a2);
    }
    
    @Override
    public void setString(final long a1, final String a2, final String a3) {
        this.boundsCheck(a1, Native.getBytes(a2, a3).length + 1L);
        super.setString(a1, a2, a3);
    }
    
    @Override
    public void setWideString(final long a1, final String a2) {
        this.boundsCheck(a1, (a2.length() + 1L) * Native.WCHAR_SIZE);
        super.setWideString(a1, a2);
    }
    
    @Override
    public String toString() {
        return "allocated@0x" + Long.toHexString(this.peer) + " (" + this.size + " bytes)";
    }
    
    protected static void free(final long a1) {
        if (a1 != 0L) {
            Native.free(a1);
        }
    }
    
    protected static long malloc(final long a1) {
        return Native.malloc(a1);
    }
    
    public String dump() {
        return this.dump(0L, (int)this.size());
    }
    
    static {
        allocatedMemory = Collections.synchronizedMap(new WeakHashMap<Memory, Reference<Memory>>());
        buffers = new WeakMemoryHolder();
    }
    
    private class SharedMemory extends Memory
    {
        final /* synthetic */ Memory this$0;
        
        public SharedMemory(final Memory this$0, final long a1, final long a2) {
            this.this$0 = this$0;
            super();
            this.size = a2;
            this.peer = this$0.peer + a1;
        }
        
        @Override
        protected void dispose() {
            this.peer = 0L;
        }
        
        @Override
        protected void boundsCheck(final long a1, final long a2) {
            this.this$0.boundsCheck(this.peer - this.this$0.peer + a1, a2);
        }
        
        @Override
        public String toString() {
            return super.toString() + " (shared from " + this.this$0.toString() + ")";
        }
    }
}
