package javassist.bytecode;

abstract static class Branch
{
    int pos;
    int orgPos;
    
    Branch(final int a1) {
        super();
        this.orgPos = a1;
        this.pos = a1;
    }
    
    void shift(final int a1, final int a2, final boolean a3) {
        if (a1 < this.pos || (a1 == this.pos && a3)) {
            this.pos += a2;
        }
    }
    
    static int shiftOffset(final int a1, int a2, final int a3, final int a4, final boolean a5) {
        final int v1 = a1 + a2;
        if (a1 < a3) {
            if (a3 < v1 || (a5 && a3 == v1)) {
                a2 += a4;
            }
        }
        else if (a1 == a3) {
            if (v1 < a3 && a5) {
                a2 -= a4;
            }
            else if (a3 < v1 && !a5) {
                a2 += a4;
            }
        }
        else if (v1 < a3 || (!a5 && a3 == v1)) {
            a2 -= a4;
        }
        return a2;
    }
    
    boolean expanded() {
        return false;
    }
    
    int gapChanged() {
        return 0;
    }
    
    int deltaSize() {
        return 0;
    }
    
    abstract int write(final int p0, final byte[] p1, final int p2, final byte[] p3) throws BadBytecode;
}
