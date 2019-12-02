package javassist.bytecode;

abstract static class Switcher extends Branch
{
    int gap;
    int defaultByte;
    int[] offsets;
    Pointers pointers;
    
    Switcher(final int a1, final int a2, final int[] a3, final Pointers a4) {
        super(a1);
        this.gap = 3 - (a1 & 0x3);
        this.defaultByte = a2;
        this.offsets = a3;
        this.pointers = a4;
    }
    
    @Override
    void shift(final int a3, final int v1, final boolean v2) {
        final int v3 = this.pos;
        this.defaultByte = Branch.shiftOffset(v3, this.defaultByte, a3, v1, v2);
        for (int v4 = this.offsets.length, a4 = 0; a4 < v4; ++a4) {
            this.offsets[a4] = Branch.shiftOffset(v3, this.offsets[a4], a3, v1, v2);
        }
        super.shift(a3, v1, v2);
    }
    
    @Override
    int gapChanged() {
        final int v0 = 3 - (this.pos & 0x3);
        if (v0 > this.gap) {
            final int v2 = v0 - this.gap;
            this.gap = v0;
            return v2;
        }
        return 0;
    }
    
    @Override
    int deltaSize() {
        return this.gap - (3 - (this.orgPos & 0x3));
    }
    
    @Override
    int write(final int a1, final byte[] a2, int a3, final byte[] a4) throws BadBytecode {
        int v1 = 3 - (this.pos & 0x3);
        int v2 = this.gap - v1;
        final int v3 = 5 + (3 - (this.orgPos & 0x3)) + this.tableSize();
        if (v2 > 0) {
            this.adjustOffsets(v3, v2);
        }
        a4[a3++] = a2[a1];
        while (v1-- > 0) {
            a4[a3++] = 0;
        }
        ByteArray.write32bit(this.defaultByte, a4, a3);
        final int v4 = this.write2(a3 + 4, a4);
        a3 += v4 + 4;
        while (v2-- > 0) {
            a4[a3++] = 0;
        }
        return 5 + (3 - (this.orgPos & 0x3)) + v4;
    }
    
    abstract int write2(final int p0, final byte[] p1);
    
    abstract int tableSize();
    
    void adjustOffsets(final int v1, final int v2) throws BadBytecode {
        this.pointers.shiftForSwitch(this.pos + v1, v2);
        if (this.defaultByte == v1) {
            this.defaultByte -= v2;
        }
        for (int a1 = 0; a1 < this.offsets.length; ++a1) {
            if (this.offsets[a1] == v1) {
                final int[] offsets = this.offsets;
                final int n = a1;
                offsets[n] -= v2;
            }
        }
    }
}
