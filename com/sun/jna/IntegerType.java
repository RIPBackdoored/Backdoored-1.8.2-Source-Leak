package com.sun.jna;

public abstract class IntegerType extends Number implements NativeMapped
{
    private static final long serialVersionUID = 1L;
    private int size;
    private Number number;
    private boolean unsigned;
    private long value;
    
    public IntegerType(final int a1) {
        this(a1, 0L, false);
    }
    
    public IntegerType(final int a1, final boolean a2) {
        this(a1, 0L, a2);
    }
    
    public IntegerType(final int a1, final long a2) {
        this(a1, a2, false);
    }
    
    public IntegerType(final int a1, final long a2, final boolean a3) {
        super();
        this.size = a1;
        this.unsigned = a3;
        this.setValue(a2);
    }
    
    public void setValue(final long v2) {
        long v3 = v2;
        this.value = v2;
        switch (this.size) {
            case 1: {
                if (this.unsigned) {
                    this.value = (v2 & 0xFFL);
                }
                v3 = (byte)v2;
                this.number = (byte)v2;
                break;
            }
            case 2: {
                if (this.unsigned) {
                    this.value = (v2 & 0xFFFFL);
                }
                v3 = (short)v2;
                this.number = (short)v2;
                break;
            }
            case 4: {
                if (this.unsigned) {
                    this.value = (v2 & 0xFFFFFFFFL);
                }
                v3 = (int)v2;
                this.number = (int)v2;
                break;
            }
            case 8: {
                this.number = v2;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported size: " + this.size);
            }
        }
        if (this.size < 8) {
            final long a1 = ~((1L << this.size * 8) - 1L);
            if ((v2 < 0L && v3 != v2) || (v2 >= 0L && (a1 & v2) != 0x0L)) {
                throw new IllegalArgumentException("Argument value 0x" + Long.toHexString(v2) + " exceeds native capacity (" + this.size + " bytes) mask=0x" + Long.toHexString(a1));
            }
        }
    }
    
    @Override
    public Object toNative() {
        return this.number;
    }
    
    @Override
    public Object fromNative(final Object v-3, final FromNativeContext v-2) {
        final long value = (v-3 == null) ? 0L : ((Number)v-3).longValue();
        try {
            final IntegerType a1 = (IntegerType)this.getClass().newInstance();
            a1.setValue(value);
            return a1;
        }
        catch (InstantiationException a2) {
            throw new IllegalArgumentException("Can't instantiate " + this.getClass());
        }
        catch (IllegalAccessException v1) {
            throw new IllegalArgumentException("Not allowed to instantiate " + this.getClass());
        }
    }
    
    @Override
    public Class<?> nativeType() {
        return this.number.getClass();
    }
    
    @Override
    public int intValue() {
        return (int)this.value;
    }
    
    @Override
    public long longValue() {
        return this.value;
    }
    
    @Override
    public float floatValue() {
        return this.number.floatValue();
    }
    
    @Override
    public double doubleValue() {
        return this.number.doubleValue();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof IntegerType && this.number.equals(((IntegerType)a1).number);
    }
    
    @Override
    public String toString() {
        return this.number.toString();
    }
    
    @Override
    public int hashCode() {
        return this.number.hashCode();
    }
    
    public static <T extends IntegerType> int compare(final T a1, final T a2) {
        if (a1 == a2) {
            return 0;
        }
        if (a1 == null) {
            return 1;
        }
        if (a2 == null) {
            return -1;
        }
        return compare(a1.longValue(), a2.longValue());
    }
    
    public static int compare(final IntegerType a1, final long a2) {
        if (a1 == null) {
            return 1;
        }
        return compare(a1.longValue(), a2);
    }
    
    public static final int compare(final long a1, final long a2) {
        if (a1 == a2) {
            return 0;
        }
        if (a1 < a2) {
            return -1;
        }
        return 1;
    }
}
