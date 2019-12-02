package javassist.compiler.ast;

import javassist.compiler.*;

public class IntConst extends ASTree
{
    protected long value;
    protected int type;
    
    public IntConst(final long a1, final int a2) {
        super();
        this.value = a1;
        this.type = a2;
    }
    
    public long get() {
        return this.value;
    }
    
    public void set(final long a1) {
        this.value = a1;
    }
    
    public int getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return Long.toString(this.value);
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atIntConst(this);
    }
    
    public ASTree compute(final int a1, final ASTree a2) {
        if (a2 instanceof IntConst) {
            return this.compute0(a1, (IntConst)a2);
        }
        if (a2 instanceof DoubleConst) {
            return this.compute0(a1, (DoubleConst)a2);
        }
        return null;
    }
    
    private IntConst compute0(final int v-8, final IntConst v-7) {
        final int type = this.type;
        final int type2 = v-7.type;
        int a3 = 0;
        if (type == 403 || type2 == 403) {
            final int a1 = 403;
        }
        else if (type == 401 && type2 == 401) {
            final int a2 = 401;
        }
        else {
            a3 = 402;
        }
        final long value = this.value;
        final long value2 = v-7.value;
        long v1 = 0L;
        switch (v-8) {
            case 43: {
                v1 = value + value2;
                break;
            }
            case 45: {
                v1 = value - value2;
                break;
            }
            case 42: {
                v1 = value * value2;
                break;
            }
            case 47: {
                v1 = value / value2;
                break;
            }
            case 37: {
                v1 = value % value2;
                break;
            }
            case 124: {
                v1 = (value | value2);
                break;
            }
            case 94: {
                v1 = (value ^ value2);
                break;
            }
            case 38: {
                v1 = (value & value2);
                break;
            }
            case 364: {
                v1 = this.value << (int)value2;
                a3 = type;
                break;
            }
            case 366: {
                v1 = this.value >> (int)value2;
                a3 = type;
                break;
            }
            case 370: {
                v1 = this.value >>> (int)value2;
                a3 = type;
                break;
            }
            default: {
                return null;
            }
        }
        return new IntConst(v1, a3);
    }
    
    private DoubleConst compute0(final int v-5, final DoubleConst v-4) {
        final double n = (double)this.value;
        final double value = v-4.value;
        double v1 = 0.0;
        switch (v-5) {
            case 43: {
                final double a1 = n + value;
                break;
            }
            case 45: {
                final double a2 = n - value;
                break;
            }
            case 42: {
                v1 = n * value;
                break;
            }
            case 47: {
                v1 = n / value;
                break;
            }
            case 37: {
                v1 = n % value;
                break;
            }
            default: {
                return null;
            }
        }
        return new DoubleConst(v1, v-4.type);
    }
}
