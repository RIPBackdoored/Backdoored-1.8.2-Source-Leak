package com.sun.jna;

import java.lang.reflect.*;
import java.nio.*;
import java.util.*;
import java.io.*;

public class Pointer
{
    public static final int SIZE;
    public static final Pointer NULL;
    protected long peer;
    
    public static final Pointer createConstant(final long a1) {
        return new Opaque(a1);
    }
    
    public static final Pointer createConstant(final int a1) {
        return new Opaque((long)a1 & -1L);
    }
    
    Pointer() {
        super();
    }
    
    public Pointer(final long a1) {
        super();
        this.peer = a1;
    }
    
    public Pointer share(final long a1) {
        return this.share(a1, 0L);
    }
    
    public Pointer share(final long a1, final long a2) {
        if (a1 == 0L) {
            return this;
        }
        return new Pointer(this.peer + a1);
    }
    
    public void clear(final long a1) {
        this.setMemory(0L, a1, (byte)0);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 == this || (a1 != null && a1 instanceof Pointer && ((Pointer)a1).peer == this.peer);
    }
    
    @Override
    public int hashCode() {
        return (int)((this.peer >>> 32) + (this.peer & -1L));
    }
    
    public long indexOf(final long a1, final byte a2) {
        return Native.indexOf(this, this.peer, a1, a2);
    }
    
    public void read(final long a1, final byte[] a2, final int a3, final int a4) {
        Native.read(this, this.peer, a1, a2, a3, a4);
    }
    
    public void read(final long a1, final short[] a2, final int a3, final int a4) {
        Native.read(this, this.peer, a1, a2, a3, a4);
    }
    
    public void read(final long a1, final char[] a2, final int a3, final int a4) {
        Native.read(this, this.peer, a1, a2, a3, a4);
    }
    
    public void read(final long a1, final int[] a2, final int a3, final int a4) {
        Native.read(this, this.peer, a1, a2, a3, a4);
    }
    
    public void read(final long a1, final long[] a2, final int a3, final int a4) {
        Native.read(this, this.peer, a1, a2, a3, a4);
    }
    
    public void read(final long a1, final float[] a2, final int a3, final int a4) {
        Native.read(this, this.peer, a1, a2, a3, a4);
    }
    
    public void read(final long a1, final double[] a2, final int a3, final int a4) {
        Native.read(this, this.peer, a1, a2, a3, a4);
    }
    
    public void read(final long v1, final Pointer[] v3, final int v4, final int v5) {
        for (int a3 = 0; a3 < v5; ++a3) {
            final Pointer a4 = this.getPointer(v1 + a3 * Pointer.SIZE);
            final Pointer a5 = v3[a3 + v4];
            if (a5 == null || a4 == null || a4.peer != a5.peer) {
                v3[a3 + v4] = a4;
            }
        }
    }
    
    public void write(final long a1, final byte[] a2, final int a3, final int a4) {
        Native.write(this, this.peer, a1, a2, a3, a4);
    }
    
    public void write(final long a1, final short[] a2, final int a3, final int a4) {
        Native.write(this, this.peer, a1, a2, a3, a4);
    }
    
    public void write(final long a1, final char[] a2, final int a3, final int a4) {
        Native.write(this, this.peer, a1, a2, a3, a4);
    }
    
    public void write(final long a1, final int[] a2, final int a3, final int a4) {
        Native.write(this, this.peer, a1, a2, a3, a4);
    }
    
    public void write(final long a1, final long[] a2, final int a3, final int a4) {
        Native.write(this, this.peer, a1, a2, a3, a4);
    }
    
    public void write(final long a1, final float[] a2, final int a3, final int a4) {
        Native.write(this, this.peer, a1, a2, a3, a4);
    }
    
    public void write(final long a1, final double[] a2, final int a3, final int a4) {
        Native.write(this, this.peer, a1, a2, a3, a4);
    }
    
    public void write(final long a3, final Pointer[] a4, final int v1, final int v2) {
        for (int a5 = 0; a5 < v2; ++a5) {
            this.setPointer(a3 + a5 * Pointer.SIZE, a4[v1 + a5]);
        }
    }
    
    Object getValue(final long v-4, final Class<?> v-2, final Object v-1) {
        Object v0 = null;
        if (Structure.class.isAssignableFrom(v-2)) {
            Structure a1 = (Structure)v-1;
            if (Structure.ByReference.class.isAssignableFrom(v-2)) {
                a1 = Structure.updateStructureByReference(v-2, a1, this.getPointer(v-4));
            }
            else {
                a1.useMemory(this, (int)v-4, true);
                a1.read();
            }
            v0 = a1;
        }
        else if (v-2 == Boolean.TYPE || v-2 == Boolean.class) {
            v0 = Function.valueOf(this.getInt(v-4) != 0);
        }
        else if (v-2 == Byte.TYPE || v-2 == Byte.class) {
            v0 = this.getByte(v-4);
        }
        else if (v-2 == Short.TYPE || v-2 == Short.class) {
            v0 = this.getShort(v-4);
        }
        else if (v-2 == Character.TYPE || v-2 == Character.class) {
            v0 = this.getChar(v-4);
        }
        else if (v-2 == Integer.TYPE || v-2 == Integer.class) {
            v0 = this.getInt(v-4);
        }
        else if (v-2 == Long.TYPE || v-2 == Long.class) {
            v0 = this.getLong(v-4);
        }
        else if (v-2 == Float.TYPE || v-2 == Float.class) {
            v0 = this.getFloat(v-4);
        }
        else if (v-2 == Double.TYPE || v-2 == Double.class) {
            v0 = this.getDouble(v-4);
        }
        else if (Pointer.class.isAssignableFrom(v-2)) {
            final Pointer a2 = this.getPointer(v-4);
            if (a2 != null) {
                final Pointer a3 = (v-1 instanceof Pointer) ? ((Pointer)v-1) : null;
                if (a3 == null || a2.peer != a3.peer) {
                    v0 = a2;
                }
                else {
                    v0 = a3;
                }
            }
        }
        else if (v-2 == String.class) {
            final Pointer v2 = this.getPointer(v-4);
            v0 = ((v2 != null) ? v2.getString(0L) : null);
        }
        else if (v-2 == WString.class) {
            final Pointer v2 = this.getPointer(v-4);
            v0 = ((v2 != null) ? new WString(v2.getWideString(0L)) : null);
        }
        else if (Callback.class.isAssignableFrom(v-2)) {
            final Pointer v2 = this.getPointer(v-4);
            if (v2 == null) {
                v0 = null;
            }
            else {
                Callback v3 = (Callback)v-1;
                final Pointer v4 = CallbackReference.getFunctionPointer(v3);
                if (!v2.equals(v4)) {
                    v3 = CallbackReference.getCallback(v-2, v2);
                }
                v0 = v3;
            }
        }
        else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(v-2)) {
            final Pointer v2 = this.getPointer(v-4);
            if (v2 == null) {
                v0 = null;
            }
            else {
                final Pointer v5 = (v-1 == null) ? null : Native.getDirectBufferPointer((Buffer)v-1);
                if (v5 == null || !v5.equals(v2)) {
                    throw new IllegalStateException("Can't autogenerate a direct buffer on memory read");
                }
                v0 = v-1;
            }
        }
        else if (NativeMapped.class.isAssignableFrom(v-2)) {
            final NativeMapped v6 = (NativeMapped)v-1;
            if (v6 != null) {
                final Object v7 = this.getValue(v-4, v6.nativeType(), null);
                v0 = v6.fromNative(v7, new FromNativeContext(v-2));
                if (v6.equals(v0)) {
                    v0 = v6;
                }
            }
            else {
                final NativeMappedConverter v8 = NativeMappedConverter.getInstance(v-2);
                final Object v9 = this.getValue(v-4, v8.nativeType(), null);
                v0 = v8.fromNative(v9, new FromNativeContext(v-2));
            }
        }
        else {
            if (!v-2.isArray()) {
                throw new IllegalArgumentException("Reading \"" + v-2 + "\" from memory is not supported");
            }
            v0 = v-1;
            if (v0 == null) {
                throw new IllegalStateException("Need an initialized array");
            }
            this.readArray(v-4, v0, v-2.getComponentType());
        }
        return v0;
    }
    
    private void readArray(final long v-6, final Object v-4, final Class<?> v-3) {
        int length = 0;
        length = Array.getLength(v-4);
        final Object v9 = v-4;
        if (v-3 == Byte.TYPE) {
            this.read(v-6, (byte[])v9, 0, length);
        }
        else if (v-3 == Short.TYPE) {
            this.read(v-6, (short[])v9, 0, length);
        }
        else if (v-3 == Character.TYPE) {
            this.read(v-6, (char[])v9, 0, length);
        }
        else if (v-3 == Integer.TYPE) {
            this.read(v-6, (int[])v9, 0, length);
        }
        else if (v-3 == Long.TYPE) {
            this.read(v-6, (long[])v9, 0, length);
        }
        else if (v-3 == Float.TYPE) {
            this.read(v-6, (float[])v9, 0, length);
        }
        else if (v-3 == Double.TYPE) {
            this.read(v-6, (double[])v9, 0, length);
        }
        else if (Pointer.class.isAssignableFrom(v-3)) {
            this.read(v-6, (Pointer[])v9, 0, length);
        }
        else if (Structure.class.isAssignableFrom(v-3)) {
            final Structure[] v0 = (Structure[])v9;
            if (Structure.ByReference.class.isAssignableFrom(v-3)) {
                final Pointer[] a2 = this.getPointerArray(v-6, v0.length);
                for (int a3 = 0; a3 < v0.length; ++a3) {
                    v0[a3] = Structure.updateStructureByReference(v-3, v0[a3], a2[a3]);
                }
            }
            else {
                Structure v2 = v0[0];
                if (v2 == null) {
                    v2 = Structure.newInstance(v-3, this.share(v-6));
                    v2.conditionalAutoRead();
                    v0[0] = v2;
                }
                else {
                    v2.useMemory(this, (int)v-6, true);
                    v2.read();
                }
                final Structure[] v3 = v2.toArray(v0.length);
                for (int a4 = 1; a4 < v0.length; ++a4) {
                    if (v0[a4] == null) {
                        v0[a4] = v3[a4];
                    }
                    else {
                        v0[a4].useMemory(this, (int)(v-6 + a4 * v0[a4].size()), true);
                        v0[a4].read();
                    }
                }
            }
        }
        else {
            if (!NativeMapped.class.isAssignableFrom(v-3)) {
                throw new IllegalArgumentException("Reading array of " + v-3 + " from memory not supported");
            }
            final NativeMapped[] v4 = (NativeMapped[])v9;
            final NativeMappedConverter v5 = NativeMappedConverter.getInstance(v-3);
            final int v6 = Native.getNativeSize(v9.getClass(), v9) / v4.length;
            for (int v7 = 0; v7 < v4.length; ++v7) {
                final Object v8 = this.getValue(v-6 + v6 * v7, v5.nativeType(), v4[v7]);
                v4[v7] = (NativeMapped)v5.fromNative(v8, new FromNativeContext(v-3));
            }
        }
    }
    
    public byte getByte(final long a1) {
        return Native.getByte(this, this.peer, a1);
    }
    
    public char getChar(final long a1) {
        return Native.getChar(this, this.peer, a1);
    }
    
    public short getShort(final long a1) {
        return Native.getShort(this, this.peer, a1);
    }
    
    public int getInt(final long a1) {
        return Native.getInt(this, this.peer, a1);
    }
    
    public long getLong(final long a1) {
        return Native.getLong(this, this.peer, a1);
    }
    
    public NativeLong getNativeLong(final long a1) {
        return new NativeLong((NativeLong.SIZE == 8) ? this.getLong(a1) : this.getInt(a1));
    }
    
    public float getFloat(final long a1) {
        return Native.getFloat(this, this.peer, a1);
    }
    
    public double getDouble(final long a1) {
        return Native.getDouble(this, this.peer, a1);
    }
    
    public Pointer getPointer(final long a1) {
        return Native.getPointer(this.peer + a1);
    }
    
    public ByteBuffer getByteBuffer(final long a1, final long a2) {
        return Native.getDirectByteBuffer(this, this.peer, a1, a2).order(ByteOrder.nativeOrder());
    }
    
    @Deprecated
    public String getString(final long a1, final boolean a2) {
        return a2 ? this.getWideString(a1) : this.getString(a1);
    }
    
    public String getWideString(final long a1) {
        return Native.getWideString(this, this.peer, a1);
    }
    
    public String getString(final long a1) {
        return this.getString(a1, Native.getDefaultStringEncoding());
    }
    
    public String getString(final long a1, final String a2) {
        return Native.getString(this, a1, a2);
    }
    
    public byte[] getByteArray(final long a1, final int a2) {
        final byte[] v1 = new byte[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public char[] getCharArray(final long a1, final int a2) {
        final char[] v1 = new char[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public short[] getShortArray(final long a1, final int a2) {
        final short[] v1 = new short[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public int[] getIntArray(final long a1, final int a2) {
        final int[] v1 = new int[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public long[] getLongArray(final long a1, final int a2) {
        final long[] v1 = new long[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public float[] getFloatArray(final long a1, final int a2) {
        final float[] v1 = new float[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public double[] getDoubleArray(final long a1, final int a2) {
        final double[] v1 = new double[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public Pointer[] getPointerArray(final long a1) {
        final List<Pointer> v1 = new ArrayList<Pointer>();
        int v2 = 0;
        for (Pointer v3 = this.getPointer(a1); v3 != null; v3 = this.getPointer(a1 + v2)) {
            v1.add(v3);
            v2 += Pointer.SIZE;
        }
        return v1.toArray(new Pointer[v1.size()]);
    }
    
    public Pointer[] getPointerArray(final long a1, final int a2) {
        final Pointer[] v1 = new Pointer[a2];
        this.read(a1, v1, 0, a2);
        return v1;
    }
    
    public String[] getStringArray(final long a1) {
        return this.getStringArray(a1, -1, Native.getDefaultStringEncoding());
    }
    
    public String[] getStringArray(final long a1, final String a2) {
        return this.getStringArray(a1, -1, a2);
    }
    
    public String[] getStringArray(final long a1, final int a2) {
        return this.getStringArray(a1, a2, Native.getDefaultStringEncoding());
    }
    
    @Deprecated
    public String[] getStringArray(final long a1, final boolean a2) {
        return this.getStringArray(a1, -1, a2);
    }
    
    public String[] getWideStringArray(final long a1) {
        return this.getWideStringArray(a1, -1);
    }
    
    public String[] getWideStringArray(final long a1, final int a2) {
        return this.getStringArray(a1, a2, "--WIDE-STRING--");
    }
    
    @Deprecated
    public String[] getStringArray(final long a1, final int a2, final boolean a3) {
        return this.getStringArray(a1, a2, a3 ? "--WIDE-STRING--" : Native.getDefaultStringEncoding());
    }
    
    public String[] getStringArray(final long v-6, final int v-4, final String v-3) {
        final List<String> list = new ArrayList<String>();
        int v0 = 0;
        if (v-4 != -1) {
            Pointer a3 = this.getPointer(v-6 + v0);
            int a4 = 0;
            while (a4++ < v-4) {
                final String a5 = (a3 == null) ? null : ("--WIDE-STRING--".equals(v-3) ? a3.getWideString(0L) : a3.getString(0L, v-3));
                list.add(a5);
                if (a4 < v-4) {
                    v0 += Pointer.SIZE;
                    a3 = this.getPointer(v-6 + v0);
                }
            }
        }
        else {
            Pointer pointer;
            while ((pointer = this.getPointer(v-6 + v0)) != null) {
                final String v2 = (pointer == null) ? null : ("--WIDE-STRING--".equals(v-3) ? pointer.getWideString(0L) : pointer.getString(0L, v-3));
                list.add(v2);
                v0 += Pointer.SIZE;
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    void setValue(final long v-4, final Object v-2, final Class<?> v-1) {
        if (v-1 == Boolean.TYPE || v-1 == Boolean.class) {
            this.setInt(v-4, Boolean.TRUE.equals(v-2) ? -1 : 0);
        }
        else if (v-1 == Byte.TYPE || v-1 == Byte.class) {
            this.setByte(v-4, (byte)((v-2 == null) ? 0 : ((byte)v-2)));
        }
        else if (v-1 == Short.TYPE || v-1 == Short.class) {
            this.setShort(v-4, (short)((v-2 == null) ? 0 : ((short)v-2)));
        }
        else if (v-1 == Character.TYPE || v-1 == Character.class) {
            this.setChar(v-4, (v-2 == null) ? '\0' : ((char)v-2));
        }
        else if (v-1 == Integer.TYPE || v-1 == Integer.class) {
            this.setInt(v-4, (v-2 == null) ? 0 : ((int)v-2));
        }
        else if (v-1 == Long.TYPE || v-1 == Long.class) {
            this.setLong(v-4, (v-2 == null) ? 0L : ((long)v-2));
        }
        else if (v-1 == Float.TYPE || v-1 == Float.class) {
            this.setFloat(v-4, (v-2 == null) ? 0.0f : ((float)v-2));
        }
        else if (v-1 == Double.TYPE || v-1 == Double.class) {
            this.setDouble(v-4, (v-2 == null) ? 0.0 : ((double)v-2));
        }
        else if (v-1 == Pointer.class) {
            this.setPointer(v-4, (Pointer)v-2);
        }
        else if (v-1 == String.class) {
            this.setPointer(v-4, (Pointer)v-2);
        }
        else if (v-1 == WString.class) {
            this.setPointer(v-4, (Pointer)v-2);
        }
        else if (Structure.class.isAssignableFrom(v-1)) {
            final Structure a1 = (Structure)v-2;
            if (Structure.ByReference.class.isAssignableFrom(v-1)) {
                this.setPointer(v-4, (a1 == null) ? null : a1.getPointer());
                if (a1 != null) {
                    a1.autoWrite();
                }
            }
            else {
                a1.useMemory(this, (int)v-4, true);
                a1.write();
            }
        }
        else if (Callback.class.isAssignableFrom(v-1)) {
            this.setPointer(v-4, CallbackReference.getFunctionPointer((Callback)v-2));
        }
        else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(v-1)) {
            final Pointer a2 = (v-2 == null) ? null : Native.getDirectBufferPointer((Buffer)v-2);
            this.setPointer(v-4, a2);
        }
        else if (NativeMapped.class.isAssignableFrom(v-1)) {
            final NativeMappedConverter a3 = NativeMappedConverter.getInstance(v-1);
            final Class<?> v1 = a3.nativeType();
            this.setValue(v-4, a3.toNative(v-2, new ToNativeContext()), v1);
        }
        else {
            if (!v-1.isArray()) {
                throw new IllegalArgumentException("Writing " + v-1 + " to memory is not supported");
            }
            this.writeArray(v-4, v-2, v-1.getComponentType());
        }
    }
    
    private void writeArray(final long v-3, final Object v-1, final Class<?> v0) {
        if (v0 == Byte.TYPE) {
            final byte[] a1 = (byte[])v-1;
            this.write(v-3, a1, 0, a1.length);
        }
        else if (v0 == Short.TYPE) {
            final short[] a2 = (short[])v-1;
            this.write(v-3, a2, 0, a2.length);
        }
        else if (v0 == Character.TYPE) {
            final char[] a3 = (char[])v-1;
            this.write(v-3, a3, 0, a3.length);
        }
        else if (v0 == Integer.TYPE) {
            final int[] v = (int[])v-1;
            this.write(v-3, v, 0, v.length);
        }
        else if (v0 == Long.TYPE) {
            final long[] v2 = (long[])v-1;
            this.write(v-3, v2, 0, v2.length);
        }
        else if (v0 == Float.TYPE) {
            final float[] v3 = (float[])v-1;
            this.write(v-3, v3, 0, v3.length);
        }
        else if (v0 == Double.TYPE) {
            final double[] v4 = (double[])v-1;
            this.write(v-3, v4, 0, v4.length);
        }
        else if (Pointer.class.isAssignableFrom(v0)) {
            final Pointer[] v5 = (Pointer[])v-1;
            this.write(v-3, v5, 0, v5.length);
        }
        else if (Structure.class.isAssignableFrom(v0)) {
            final Structure[] v6 = (Structure[])v-1;
            if (Structure.ByReference.class.isAssignableFrom(v0)) {
                final Pointer[] v7 = new Pointer[v6.length];
                for (int v8 = 0; v8 < v6.length; ++v8) {
                    if (v6[v8] == null) {
                        v7[v8] = null;
                    }
                    else {
                        v7[v8] = v6[v8].getPointer();
                        v6[v8].write();
                    }
                }
                this.write(v-3, v7, 0, v7.length);
            }
            else {
                Structure v9 = v6[0];
                if (v9 == null) {
                    v9 = Structure.newInstance(v0, this.share(v-3));
                    v6[0] = v9;
                }
                else {
                    v9.useMemory(this, (int)v-3, true);
                }
                v9.write();
                final Structure[] v10 = v9.toArray(v6.length);
                for (int v11 = 1; v11 < v6.length; ++v11) {
                    if (v6[v11] == null) {
                        v6[v11] = v10[v11];
                    }
                    else {
                        v6[v11].useMemory(this, (int)(v-3 + v11 * v6[v11].size()), true);
                    }
                    v6[v11].write();
                }
            }
        }
        else {
            if (!NativeMapped.class.isAssignableFrom(v0)) {
                throw new IllegalArgumentException("Writing array of " + v0 + " to memory not supported");
            }
            final NativeMapped[] v12 = (NativeMapped[])v-1;
            final NativeMappedConverter v13 = NativeMappedConverter.getInstance(v0);
            final Class<?> v14 = v13.nativeType();
            final int v11 = Native.getNativeSize(v-1.getClass(), v-1) / v12.length;
            for (int v15 = 0; v15 < v12.length; ++v15) {
                final Object v16 = v13.toNative(v12[v15], new ToNativeContext());
                this.setValue(v-3 + v15 * v11, v16, v14);
            }
        }
    }
    
    public void setMemory(final long a1, final long a2, final byte a3) {
        Native.setMemory(this, this.peer, a1, a2, a3);
    }
    
    public void setByte(final long a1, final byte a2) {
        Native.setByte(this, this.peer, a1, a2);
    }
    
    public void setShort(final long a1, final short a2) {
        Native.setShort(this, this.peer, a1, a2);
    }
    
    public void setChar(final long a1, final char a2) {
        Native.setChar(this, this.peer, a1, a2);
    }
    
    public void setInt(final long a1, final int a2) {
        Native.setInt(this, this.peer, a1, a2);
    }
    
    public void setLong(final long a1, final long a2) {
        Native.setLong(this, this.peer, a1, a2);
    }
    
    public void setNativeLong(final long a1, final NativeLong a2) {
        if (NativeLong.SIZE == 8) {
            this.setLong(a1, a2.longValue());
        }
        else {
            this.setInt(a1, a2.intValue());
        }
    }
    
    public void setFloat(final long a1, final float a2) {
        Native.setFloat(this, this.peer, a1, a2);
    }
    
    public void setDouble(final long a1, final double a2) {
        Native.setDouble(this, this.peer, a1, a2);
    }
    
    public void setPointer(final long a1, final Pointer a2) {
        Native.setPointer(this, this.peer, a1, (a2 != null) ? a2.peer : 0L);
    }
    
    @Deprecated
    public void setString(final long a1, final String a2, final boolean a3) {
        if (a3) {
            this.setWideString(a1, a2);
        }
        else {
            this.setString(a1, a2);
        }
    }
    
    public void setWideString(final long a1, final String a2) {
        Native.setWideString(this, this.peer, a1, a2);
    }
    
    public void setString(final long a1, final WString a2) {
        this.setWideString(a1, (a2 == null) ? null : a2.toString());
    }
    
    public void setString(final long a1, final String a2) {
        this.setString(a1, a2, Native.getDefaultStringEncoding());
    }
    
    public void setString(final long a1, final String a2, final String a3) {
        final byte[] v1 = Native.getBytes(a2, a3);
        this.write(a1, v1, 0, v1.length);
        this.setByte(a1 + v1.length, (byte)0);
    }
    
    public String dump(final long v2, final int v4) {
        final int v5 = 4;
        final String v6 = "memory dump";
        final StringWriter v7 = new StringWriter("memory dump".length() + 2 + v4 * 2 + v4 / 4 * 4);
        final PrintWriter v8 = new PrintWriter(v7);
        v8.println("memory dump");
        for (int a2 = 0; a2 < v4; ++a2) {
            final byte a3 = this.getByte(v2 + a2);
            if (a2 % 4 == 0) {
                v8.print("[");
            }
            if (a3 >= 0 && a3 < 16) {
                v8.print("0");
            }
            v8.print(Integer.toHexString(a3 & 0xFF));
            if (a2 % 4 == 3 && a2 < v4 - 1) {
                v8.println("]");
            }
        }
        if (v7.getBuffer().charAt(v7.getBuffer().length() - 2) != ']') {
            v8.println("]");
        }
        return v7.toString();
    }
    
    @Override
    public String toString() {
        return "native@0x" + Long.toHexString(this.peer);
    }
    
    public static long nativeValue(final Pointer a1) {
        return (a1 == null) ? 0L : a1.peer;
    }
    
    public static void nativeValue(final Pointer a1, final long a2) {
        a1.peer = a2;
    }
    
    static {
        if ((SIZE = Native.POINTER_SIZE) == 0) {
            throw new Error("Native library not initialized");
        }
        NULL = null;
    }
    
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
}
