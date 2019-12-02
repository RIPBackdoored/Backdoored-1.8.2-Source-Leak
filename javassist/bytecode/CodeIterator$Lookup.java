package javassist.bytecode;

static class Lookup extends Switcher
{
    int[] matches;
    
    Lookup(final int a1, final int a2, final int[] a3, final int[] a4, final Pointers a5) {
        super(a1, a2, a4, a5);
        this.matches = a3;
    }
    
    @Override
    int write2(int v1, final byte[] v2) {
        final int v3 = this.matches.length;
        ByteArray.write32bit(v3, v2, v1);
        v1 += 4;
        for (int a1 = 0; a1 < v3; ++a1) {
            ByteArray.write32bit(this.matches[a1], v2, v1);
            ByteArray.write32bit(this.offsets[a1], v2, v1 + 4);
            v1 += 8;
        }
        return 4 + 8 * v3;
    }
    
    @Override
    int tableSize() {
        return 4 + 8 * this.matches.length;
    }
}
