package javassist.bytecode;

import java.util.*;

static class Copier extends SimpleCopy
{
    private ConstPool srcPool;
    private ConstPool destPool;
    private Map classnames;
    
    public Copier(final ConstPool a1, final byte[] a2, final ConstPool a3, final Map a4) {
        super(a2);
        this.srcPool = a1;
        this.destPool = a3;
        this.classnames = a4;
    }
    
    @Override
    protected int copyData(final int a1, final int a2) {
        if (a1 == 7) {
            return this.srcPool.copy(a2, this.destPool, this.classnames);
        }
        return a2;
    }
    
    @Override
    protected int[] copyData(final int[] v1, final int[] v2) {
        final int[] v3 = new int[v2.length];
        for (int a1 = 0; a1 < v2.length; ++a1) {
            if (v1[a1] == 7) {
                v3[a1] = this.srcPool.copy(v2[a1], this.destPool, this.classnames);
            }
            else {
                v3[a1] = v2[a1];
            }
        }
        return v3;
    }
}
