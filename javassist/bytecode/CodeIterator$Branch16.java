package javassist.bytecode;

abstract static class Branch16 extends Branch
{
    int offset;
    int state;
    static final int BIT16 = 0;
    static final int EXPAND = 1;
    static final int BIT32 = 2;
    
    Branch16(final int a1, final int a2) {
        super(a1);
        this.offset = a2;
        this.state = 0;
    }
    
    @Override
    void shift(final int a1, final int a2, final boolean a3) {
        this.offset = Branch.shiftOffset(this.pos, this.offset, a1, a2, a3);
        super.shift(a1, a2, a3);
        if (this.state == 0 && (this.offset < -32768 || 32767 < this.offset)) {
            this.state = 1;
        }
    }
    
    @Override
    boolean expanded() {
        if (this.state == 1) {
            this.state = 2;
            return true;
        }
        return false;
    }
    
    @Override
    abstract int deltaSize();
    
    abstract void write32(final int p0, final byte[] p1, final int p2, final byte[] p3);
    
    @Override
    int write(final int a1, final byte[] a2, final int a3, final byte[] a4) {
        if (this.state == 2) {
            this.write32(a1, a2, a3, a4);
        }
        else {
            a4[a3] = a2[a1];
            ByteArray.write16bit(this.offset, a4, a3 + 1);
        }
        return 3;
    }
}
