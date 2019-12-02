package javassist.compiler.ast;

import javassist.compiler.*;

public class DoubleConst extends ASTree
{
    protected double value;
    protected int type;
    
    public DoubleConst(final double a1, final int a2) {
        super();
        this.value = a1;
        this.type = a2;
    }
    
    public double get() {
        return this.value;
    }
    
    public void set(final double a1) {
        this.value = a1;
    }
    
    public int getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return Double.toString(this.value);
    }
    
    @Override
    public void accept(final Visitor a1) throws CompileError {
        a1.atDoubleConst(this);
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
    
    private DoubleConst compute0(final int v1, final DoubleConst v2) {
        int v3 = 0;
        if (this.type == 405 || v2.type == 405) {
            final int a1 = 405;
        }
        else {
            v3 = 404;
        }
        return compute(v1, this.value, v2.value, v3);
    }
    
    private DoubleConst compute0(final int a1, final IntConst a2) {
        return compute(a1, this.value, (double)a2.value, this.type);
    }
    
    private static DoubleConst compute(final int v-5, final double v-4, final double v-2, final int v0) {
        final double v;
        switch (v-5) {
            case 43: {
                final double a1 = v-4 + v-2;
                break;
            }
            case 45: {
                final double a2 = v-4 - v-2;
                break;
            }
            case 42: {
                final double a3 = v-4 * v-2;
                break;
            }
            case 47: {
                final double a4 = v-4 / v-2;
                break;
            }
            case 37: {
                v = v-4 % v-2;
                break;
            }
            default: {
                return null;
            }
        }
        return new DoubleConst(v, v0);
    }
}
