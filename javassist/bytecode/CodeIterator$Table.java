package javassist.bytecode;

static class Table extends Switcher
{
    int low;
    int high;
    
    Table(final int a1, final int a2, final int a3, final int a4, final int[] a5, final Pointers a6) {
        super(a1, a2, a5, a6);
        this.low = a3;
        this.high = a4;
    }
    
    @Override
    int write2(int v1, final byte[] v2) {
        ByteArray.write32bit(this.low, v2, v1);
        ByteArray.write32bit(this.high, v2, v1 + 4);
        final int v3 = this.offsets.length;
        v1 += 8;
        for (int a1 = 0; a1 < v3; ++a1) {
            ByteArray.write32bit(this.offsets[a1], v2, v1);
            v1 += 4;
        }
        return 8 + 4 * v3;
    }
    
    @Override
    int tableSize() {
        return 8 + 4 * this.offsets.length;
    }
}
