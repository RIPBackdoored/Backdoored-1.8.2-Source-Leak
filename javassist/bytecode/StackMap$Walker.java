package javassist.bytecode;

public static class Walker
{
    byte[] info;
    
    public Walker(final StackMap a1) {
        super();
        this.info = a1.get();
    }
    
    public void visit() {
        final int u16bit = ByteArray.readU16bit(this.info, 0);
        int n = 2;
        for (int v0 = 0; v0 < u16bit; ++v0) {
            final int v2 = ByteArray.readU16bit(this.info, n);
            final int v3 = ByteArray.readU16bit(this.info, n + 2);
            n = this.locals(n + 4, v2, v3);
            final int v4 = ByteArray.readU16bit(this.info, n);
            n = this.stack(n + 2, v2, v4);
        }
    }
    
    public int locals(final int a1, final int a2, final int a3) {
        return this.typeInfoArray(a1, a2, a3, true);
    }
    
    public int stack(final int a1, final int a2, final int a3) {
        return this.typeInfoArray(a1, a2, a3, false);
    }
    
    public int typeInfoArray(int a3, final int a4, final int v1, final boolean v2) {
        for (int a5 = 0; a5 < v1; ++a5) {
            a3 = this.typeInfoArray2(a5, a3);
        }
        return a3;
    }
    
    int typeInfoArray2(final int v2, int v3) {
        final byte v4 = this.info[v3];
        if (v4 == 7) {
            final int a1 = ByteArray.readU16bit(this.info, v3 + 1);
            this.objectVariable(v3, a1);
            v3 += 3;
        }
        else if (v4 == 8) {
            final int a2 = ByteArray.readU16bit(this.info, v3 + 1);
            this.uninitialized(v3, a2);
            v3 += 3;
        }
        else {
            this.typeInfo(v3, v4);
            ++v3;
        }
        return v3;
    }
    
    public void typeInfo(final int a1, final byte a2) {
    }
    
    public void objectVariable(final int a1, final int a2) {
    }
    
    public void uninitialized(final int a1, final int a2) {
    }
}
