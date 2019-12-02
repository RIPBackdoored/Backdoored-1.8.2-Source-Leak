package javassist.bytecode;

static class Jump32 extends Branch
{
    int offset;
    
    Jump32(final int a1, final int a2) {
        super(a1);
        this.offset = a2;
    }
    
    @Override
    void shift(final int a1, final int a2, final boolean a3) {
        this.offset = Branch.shiftOffset(this.pos, this.offset, a1, a2, a3);
        super.shift(a1, a2, a3);
    }
    
    @Override
    int write(final int a1, final byte[] a2, final int a3, final byte[] a4) {
        a4[a3] = a2[a1];
        ByteArray.write32bit(this.offset, a4, a3 + 1);
        return 5;
    }
}
